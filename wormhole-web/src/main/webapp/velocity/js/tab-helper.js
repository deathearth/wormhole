define(['lodash', 'jquery'], function(_, $) {
  var paretnWin = window.parent
  var tabManager
  if (paretnWin && paretnWin.tabManager) {
    tabManager = paretnWin.tabManager
  }

  var parentCloseFlag = '**parent_destroy**'
  var childCloseFlag = '**child_destroy**'

  var __emptyFunc = function () { }
  var __alone = {
    setTitle: function (title) {
      document.title = title
    },
    openTab: function (url, name, query) {
      window.open(url)
    },
    openChildTab: function (url, name, query) {
      window.open(url)
    },
    closeChildTab: __emptyFunc,
    closeChildren: __emptyFunc,
    close: function () {
      window.close()
    },
    toChildren: __emptyFunc,
    toParent: __emptyFunc,
    subscribe: __emptyFunc,
    onChildClose: __emptyFunc,
    onParentClose: __emptyFunc
  }

  var TabHelper = function () {
    if (tabManager) {
      this.init()
    } else {
      this.runAlonePage = true
      this.__proto__ = __alone
      console.log('TabPage is running in alone page now')
    }
  }
  TabHelper.prototype.init = function () {
    this.id = window.frameElement.id
    this.tab = tabManager.getTab(this.id)
  }
  TabHelper.prototype.setTitle = function (title) {
    this.tab.setTitle(title)
  }
  TabHelper.prototype.openTab = function (url, name, query) {
    tabManager.open(url, name)
  }
  TabHelper.prototype.openChildTab = function (url, name, query) {
    tabManager.open(url, name, query, this.id)
  }
  TabHelper.prototype.closeChildTab = function (url) {
    var children = tabManager.getChildTabs(this.id)
    _.each(children, function (tab) {
      if (tab.options.url === url) {
        tab.destroy()
      }
    })
  }
  TabHelper.prototype.closeChildren = function () {
    var children = tabManager.getChildTabs(this.id)
    _.each(children, function (tab) {
      tab.destroy()
    })
  }
  TabHelper.prototype.close = function (data) {
    this.toChildren({ data: data }, parentCloseFlag)
    this.toParent({ url: this.tab.options.url, data: data }, childCloseFlag)
    this.tab.destroy()
  }
  TabHelper.prototype.toChildren = function (msg, from) {
    var children = tabManager.getChildTabs(this.id)
    if (!from) from = 'parent'
    _.each(children, function (tab) {
      tab.publish(from, msg)
    })
  }
  TabHelper.prototype.toParent = function (msg, from) {
    if (this.tab.parentId) {
      var parentTab = tabManager.getTab(this.tab.parentId)
      if (!from) from = 'child'
      if (parentTab) parentTab.publish(from, msg)
    }
  }
  TabHelper.prototype.subscribe = function (callback) {
    this.tab.subscribe(function (from, msg) {
      if (from !== childCloseFlag && from !== parentCloseFlag) {
        callback(from, msg)
      }
    })
  }
  TabHelper.prototype.onChildClose = function (callback) {
    this.tab.subscribe(function (from, msg) {
      if (from === childCloseFlag) {
        callback(msg)
      }
    })
  }
  TabHelper.prototype.onParentClose = function (callback) {
    this.tab.subscribe(function (from, msg) {
      if (from === parentCloseFlag) {
        callback(msg)
      }
    })
  }

  var tabHelper = new TabHelper()
  return tabHelper
})