(function (window) {
  var newId = (function () {
    var __i = 10000
    return function () {
      return (__i++).toString()
    }
  })()
  var concatUrl = function (url, query) {
    if (url.indexOf('?') >= 0) {
      url = url + '&' + query
    } else {
      url = url + '?' + query
    }
    return url
  }
  var tabCounts = 0

  var ContextMenu = function (injector) {
    this.injector = injector
    this.init()
  }
  ContextMenu.prototype.init = function () {
    var markup = '<div class="tab-menu">' +
      '<ul>' +
        '<li action="refresh">重新加载</li>' +
        '<li class="hr"></li>' +
        '<li action="close">关闭标签页</li>' +
        '<li action="closeOther">关闭其他标签页</li>' +
        '<li action="closeLeft">关闭左侧标签页</li>' +
        '<li action="closeRight">关闭右侧标签页</li>' +
      '</ul>' +
      '<div class="event-mask"></div>' +
    '</div>'
    this.$el = $(markup).appendTo(document.body)
    this.$list = this.$el.find('ul')

    this.bindEvent()
  }
  ContextMenu.prototype.bind = function (id, pid) {
    this.tabContext = {
      id: id,
      pid: pid
    }
  }
  ContextMenu.prototype.bindEvent = function () {
    var _this = this
    this.$el.on('click', function (ev) {
      _this.hide()
    })
    this.$el.on('contextmenu', function (ev) {
      _this.hide()
      return false
    })
    this.$list.on('click', 'li', function () {
      var action = $(this).attr('action')
      _this.injector.commit('contextmenu:' + action, _this.tabContext)
    })
  }
  ContextMenu.prototype.show = function (pos) {
    this.$el.show()
    var edge = window.innerWidth -  this.$list.width()

    if (pos.left > edge) {
      pos.left = edge
    }

    this.$list.css({ 
      top: pos.top + 'px', 
      left: pos.left + 'px' 
    })
  }
  ContextMenu.prototype.hide = function () {
    this.$el.hide()
  }

  var TabItem = function (options) {
    this.init(options)
  }
  TabItem.prototype.init = function (options) {
    var _this = this

    this.options = options
    this.isActive = false
    this.titleSeted = !!options.name

    this.id = newId()
    this.parentId = options.parentId

    this.$tab = $('<li data-id="' + this.id + '" data-url="' + options.url + '"><a href="javascript:">' +
      (options.name || '新标签页') + '</a><i class="fa fa-times-circle"></i></li>')
    this.$content = $('<div data-id="' + this.id + '" class="tab-content">' + 
      '<iframe src="' + options.url + '" id="' + this.id + '"></iframe></div>')
    this.$iframe = this.$content.find('iframe')

    this.$tab.on('click', 'a', function (e) {
      _this.active()
    })
    this.$tab.on('click', '.fa', function (e) {
      _this.destroy()
    })
    this.$tab.on('contextmenu', function (e) {
      e.preventDefault()
      _this.trigger('contextmenu', { event: e, id: _this.id, pid: _this.parentId })
    })
    this.$iframe.on('load', function (e) {
      if (!_this.titleSeted) {
        var pageTitle
        try {
          pageTitle = e.target.contentDocument.title  // 跨域时无法获取
        } catch (ex) {
          pageTitle = null
        }
        if (pageTitle && pageTitle.length > 0) {
          _this.setTitle(pageTitle)
        }
      }
    })

    this.subscribers = []
    tabCounts++
  }
  TabItem.prototype.active = function () {
    if (this.isActive) { return }
    this.$tab.addClass('active')
    this.$content.show()

    this.isActive = true
    this.trigger('active', { id: this.id })
  }
  TabItem.prototype.inactive = function () {
    if (!this.isActive) { return }
    this.$tab.removeClass('active')
    this.$content.hide()

    this.isActive = false
  }
  TabItem.prototype.query = function (query) {
    var url = concatUrl(this.options.url, query)
    this.$iframe.attr('src', url)
  }
  TabItem.prototype.destroy = function () {
    this.$tab.unbind()
    while (this.subscribers.length > 0) {
      this.subscribers.pop()
    }

    var siblingsId = null
    var siblings = this.$tab.next()
    if (siblings && siblings.length > 0) {
      siblingsId = siblings.data('id')
    } else {
      siblings = this.$tab.prev()
      if (siblings && siblings.length > 0) {
        siblingsId = siblings.data('id')
      }
    }

    this.$tab.remove()
    this.$content.remove()
    this.trigger('destroy', { isActive: this.isActive, id: this.id, parentId: this.parentId, siblingsId: siblingsId })

    this.options = null
    tabCounts--
  }
  TabItem.prototype.trigger = function (eventName, eventTarget) {
    this.options.injector.commit(eventName, eventTarget)
  }
  TabItem.prototype.subscribe = function (callback) {
    this.subscribers.push(callback)
  }
  TabItem.prototype.publish = function (from, msg) {
    _.each(this.subscribers, function (subscriber) {
      subscriber(from, msg)
    })
  }
  TabItem.prototype.setTitle = function (title) {
    this.titleSeted = true
    this.$tab.find('a').text(title)
    this.trigger('titleChange')
  }
  TabItem.prototype.reload= function () {
    var src = this.$iframe.attr('src')
    this.$iframe.attr('src', src)
  }

  var TabInjector = function () {
    this.eventList = {}
  }
  TabInjector.prototype.on = function (eventName, callback) {
    if (!this.eventList[eventName]) {
      this.eventList[eventName] = []
    }
    this.eventList[eventName].push(callback)
  }
  TabInjector.prototype.commit = function (eventName, eventTarget) {
    if (this.eventList[eventName]) {
      var list = this.eventList[eventName]
      for (var i = 0; i < list.length; i++) {
        list[i](eventTarget)
      }
    }
  }

  var TabManager = function (options) {
    this.init(options)
  }
  TabManager.prototype.init = function (options) {
    var _this = this
    this.max = options.maxTabs || 5
    this.$tabs = options.tabsEl
    this.$tabBox = this.$tabs.parent()
    this.$contents = options.contentsEl

    this.tabsMap = {}
    this.urlMap = {}

    this.injector = new TabInjector()
    this.contextMenu = new ContextMenu(this.injector)

    this.bindActive()
    this.bindDestroy()
    this.bindTitleChange()
    this.bindContextMenu()
    this.initArrow()
  }
  TabManager.prototype.open = function (url, name, query, parentId) {
    var currentId = this.urlMap[url]
    if (currentId) {
      this.tabsMap[currentId].active()
      if (query) {
        this.tabsMap[currentId].query(query)
      }
    } else {
      if (tabCounts >= this.max) {
        alert('最多限制打开' + this.max + '个标签页')
        return
      }
      if (query) {
        url = concatUrl(url, query)
      }

      var newTab = new TabItem({ url: url, name: name, injector: this.injector, parentId: parentId })
      this.tabsMap[newTab.id] = newTab
      this.urlMap[url] = newTab.id

      this.$tabs.append(newTab.$tab)
      this.$contents.append(newTab.$content)
      this.computeEdge()

      newTab.active()
    }
  }
  TabManager.prototype.bindDestroy = function () {
    var _this = this
    this.injector.on('destroy', function (ev) {
      // 删除引用
      delete _this.tabsMap[ev.id]
      for(var key in _this.urlMap) {
        if (_this.urlMap[key] === ev.id) {
          delete _this.urlMap[key]
          break
        }
      }
      // 销毁子页面
      for (var key in _this.tabsMap) {
        if (_this.tabsMap[key].parentId === ev.id) {
          _this.tabsMap[key].destroy()
        }
      }
      // 重新计算tab栏宽度
      _this.computeEdge()
      // 当前关闭tab非激活
      if (!ev.isActive || tabCounts === 0) {
        _this.arrowVisible()
        return 
      }
      // 当前关闭tab处于激活时，需要激活另一个tab
      // 有父tab时激活父tab
      if (ev.parentId && _this.tabsMap[ev.parentId]) {
        _this.tabsMap[ev.parentId].active()
      } else {
        // 否则激活临近标签
        if (ev.siblingsId) {
          _this.tabsMap[ev.siblingsId].active()
        }
      }
    })
  }
  TabManager.prototype.bindActive = function () {
    var _this = this
    this.injector.on('active', function (ev) {
      for(var id in _this.tabsMap) {
        if (id !== ev.id) {
          _this.tabsMap[id].inactive()
        }
      }
      _this.setScroll(_this.tabsMap[ev.id].$tab)
    })
  }
  TabManager.prototype.bindTitleChange = function () {
    var _this = this
    this.injector.on('titleChange', function (ev) { // title改变时宽度也会改变
      _this.computeEdge()

      for (var id in _this.tabsMap) {
        if (_this.tabsMap[id].isActive) {
          _this.setScroll(_this.tabsMap[id].$tab)
          break
        }
      }
    })
  }
  TabManager.prototype.bindContextMenu = function () {
    var _this = this
    this.injector.on('contextmenu', function (ev) {
      _this.contextMenu.bind(ev.id, ev.pid)
      _this.contextMenu.show({ left: ev.event.clientX, top: ev.event.clientY })
    })
    this.injector.on('contextmenu:close', function (ev) {
      _this.tabsMap[ev.id] && _this.tabsMap[ev.id].destroy()
    })
    this.injector.on('contextmenu:closeOther', function (ev) {
      for (var id in _this.tabsMap) {
        if (id !== ev.id && id !== ev.pid) {
          _this.tabsMap[id].destroy()
        }
      }
    })
    this.injector.on('contextmenu:closeLeft', function (ev) {
      var $tab = _this.tabsMap[ev.id].$tab
      var index = $tab.index()

      var leftIds = _this.$tabs.find('li:lt(' + index + ')')
        .map(function () { return $(this).data('id') }).toArray()
      _.forEach(leftIds, function (id) {
        if (id === ev.pid) { return }
        _this.tabsMap[id] && _this.tabsMap[id].destroy()
      })
    })
    this.injector.on('contextmenu:closeRight', function (ev) {
      var $tab = _this.tabsMap[ev.id].$tab
      var index = $tab.index()

      var rightIds = _this.$tabs.find('li:gt(' + index + ')')
        .map(function () { return $(this).data('id') }).toArray()
      _.forEach(rightIds, function (id) {
        if (id === ev.pid) { return }
        _this.tabsMap[id] && _this.tabsMap[id].destroy()
      })
    })
    this.injector.on('contextmenu:refresh', function (ev) {
      _this.tabsMap[ev.id].reload()
    })
  }
  TabManager.prototype.getTab = function (id) {
    return this.tabsMap[id]
  }
  TabManager.prototype.getChildTabs = function (id) {
    var childTabs = []
    for(var k in this.tabsMap) {
      if (this.tabsMap[k].parentId === id) {
        childTabs.push(this.tabsMap[k])
      }
    }
    return childTabs
  }

  TabManager.prototype.initArrow = function () {
    var _this = this
    this.$leftArrow = $('#arrow-left')
    this.$rightArrow = $('#arrow-right')

    this.$leftArrow.on('click', function () {
      var scroll = _this.$tabBox.scrollLeft() - 80
      scroll = scroll < 0 ? 0 : scroll
      _this.$tabBox.scrollLeft(scroll)
      _this.arrowVisible()
    })
    this.$rightArrow.on('click', function () {
      var scroll = _this.$tabBox.scrollLeft() + 80
      scroll = scroll > _this.scrollEdge ? _this.scrollEdge : scroll
      _this.$tabBox.scrollLeft(scroll)
      _this.arrowVisible()
    })

    this.arrowVisible()
  }
  TabManager.prototype.computeEdge = function () {
    var $last = this.$tabs.find('li').last()
    if (!$last || $last.length === 0) {
      this.scrollEdge = 0
    } else {
      var rightEdge = $last.position().left + $last.width()
      var scrollEdge = rightEdge - this.$tabBox.width()
      this.scrollEdge = Math.floor(scrollEdge)
    }

    var scroll = this.$tabBox.scrollLeft()
    if (scroll > this.scrollEdge) {
      this.$tabBox.scrollLeft(this.scrollEdge)
    }
  }
  TabManager.prototype.arrowVisible = function () {
    if (tabCounts === 0) {
      this.$rightArrow.hide()
      this.$leftArrow.hide()
      return
    }
    var scroll = this.$tabBox.scrollLeft()

    if (scroll < this.scrollEdge) {
      this.$rightArrow.show()
    } else {
      this.$rightArrow.hide()
    }

    if (scroll > 0) {
      this.$leftArrow.show()
    } else {
      this.$leftArrow.hide()
    }
  }
  TabManager.prototype.setScroll = function ($active) {
    var minScroll = $active.position().left
    var maxScroll = $active.width() + minScroll - this.$tabBox.width()
    var scroll = this.$tabBox.scrollLeft()

    if (scroll > minScroll) {
      scroll = minScroll - 20
      scroll = scroll < 0 ? 0 : scroll
    } else if (scroll < maxScroll) {
      scroll = maxScroll + 20
      scroll = scroll > this.scrollEdge ? this.scrollEdge : scroll
    } else {
      return
    }

    this.$tabBox.scrollLeft(scroll)
    this.arrowVisible()
  }

  $(function () {
    window.tabManager = new TabManager({
      tabsEl:$('#tab-navs'), 
      contentsEl: $('#tab-contents'),
      maxTabs: 20
    })
  })
})(window)