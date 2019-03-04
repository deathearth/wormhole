define(['jquery', 'lodash'], function ($, _) {
  var template = '<div class="autocomplete">\
    <ul class="autocomplete-list"></ul>\
  </div>'

  var checkOpt = function (opt) {
    if (!opt.url) {
      return false
    }
    return true
  }
  var defaultOpt = {
    value: 'value',
    noDataText: '无匹配项'
  }

  var AutoComplete = function (el, opt) {
    if (!checkOpt(opt)) { 
      return 
    }
    this.init(el, opt)
  }
  AutoComplete.prototype.init = function (el, opt) {
    this.opt = _.assign({}, defaultOpt, opt)
    this.$input = $(el)
    this.$el = $(template)
    this.$el.insertAfter(this.$input)
    this.$el.append(this.$input)

    this.empty = '<li class="empty">' + this.opt.noDataText + '</li>'

    this.$list = this.$el.find('ul')
    this.$list.css({ 
      width: this.$input.outerWidth() + 'px', 
      top: this.$input.outerHeight() + 4 + 'px' 
    })

    this.bindEvent()
  }
  AutoComplete.prototype.bindEvent = function () {
    var _this = this
    var $list = this.$list
    this.$input.on('focus', function () {
      _this.updateList()
      $list.show()
    })
    this.$input.on('blur', function (e) {
      $list.hide()
    })
    this.$input.on('input', function () {
      _this.updateList()
    })
    this.$list.on('click', 'li', function () {
      if ($(this).is('.empty')) {
        return false
      }

      var val = $(this).text()
      _this.$input.val(val).trigger('blur')
    })
    this.$list.on('wheel', function (e) {
      var scrollTop = _this.$list.prop('scrollTop')
      scrollTop -= e.originalEvent.wheelDelta
      _this.$list.prop('scrollTop', scrollTop)
      return false
    })
    this.$list.on('mousedown', function (e) {
      return false
    })
  }
  AutoComplete.prototype.getList = function (val) {
    var query = {}
    query[this.opt.value] = val

    return $.ajax({
      type: 'post',
      dataType: 'json',
      url: this.opt.url,
      processData: false,
      contentType: 'application/json',
      data: JSON.stringify(query)
    })
  }
  AutoComplete.prototype.updateList = function () {
    var _this = this
    
    if (this.xhr) { this.xhr.abort() }
    if (this.timeout) { clearTimeout(this.timeout) }

    var val = this.$input.val()
    this.timeout = setTimeout(function () {
      _this.timeout = null
      _this.xhr = _this.getList(val)

      _this.xhr.done(function (result) {
        if (result.status === 'ok') {
          _this.render(result.data)
        } else {
          _this.render(null)
        }
      }).fail(function () {
        _this.render(null)
      }).always(function () {
        _this.xhr = null
      })
    }, 200)
  }
  AutoComplete.prototype.render = function (data) {
    if (!data || data.length == 0) {
      this.$list.html(this.empty)
    } else {
      var markup = _.map(data, function (item) {
        return '<li>' + item + '</li>'
      }).join('')
      this.$list.html(markup)
    }
  }

  // 创建jquery插件
  $.fn['autoComplete'] = function (opt) {
    this.each(function (i, element) {
      new AutoComplete(element, opt)
    })
    return this
  }
})