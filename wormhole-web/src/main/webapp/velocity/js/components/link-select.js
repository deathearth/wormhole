define(['lodash', 'jquery'], function(_, $) {



  var LinkSelect = function (el, options) {
    var _this = this
    this.$el = $(el)
    this.$link = $(options.link)
    this.options = options

    this.$link.on('change', function () {
      _this.load(_this.$link.val())
    })
    this.load(this.$link.val())
  }
  LinkSelect.prototype.load = function (param) {
    var _this = this
    this.$el.prop('disabled', true)

    this.loadResource(param).done(function (options) {
      _this.render(options)
      _this.$el.prop('disabled', false)
    }).fail(function (err) {
      alert(err)
      _this.$el.prop('disabled', false)
    })
  }
  LinkSelect.prototype.loadResource = function (param) {
    var resource = this.options.resource
    var paramField = this.options.field || 'id'
    var query = {}
    query[paramField] = param

    var dtd = $.Deferred()

    if (typeof resource === 'string') {
      $.ajax({
        url: resource,
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        processData: false,
        data: JSON.stringify(query)
      }).done(function (result) {
        if (result.status === 'ok') {
          dtd.resolve(result.data)
        } else {
          dtd.reject(result.data)
        }
      }).fail(function (resp, msg, err) {
        dtd.reject(msg)
      })
    } else if (typeof resource === 'function') {
      var r = resource(param)
      if (r.done && typeof r.done === 'function') {
        dtd = r
      } else {
        dtd.resolve(r)
      }
    } else {
      var options = resource[param]
      dtd.resolve(options)
    }
    return $.when(dtd)
  }
  LinkSelect.prototype.render = function (list) {
    var textField = this.options.text || 'name'
    var valueField = this.options.value || 'id'
    var oldValue = this.$el.val()

    var markup = _.map(list, function (item) {
      return '<option value="' + item[valueField] + '" ' +
        (item[valueField] === oldValue ? 'selected' : '') +
        '>' + item[textField] + '</option>'
    })
    if (this.options.emptyItem) {
      if (typeof this.options.emptyItem === 'boolean') {
        markup.unshift('<option value="">全部</option>')
      } else if (typeof this.options.emptyItem === 'string') {
        markup.unshift('<option value="">' + this.options.emptyItem + '</option>')
      }
    }

    this.$el.html(markup.join(''))
    this.$el.trigger('change')
  }

  // 创建jquery插件
  $.fn['linkSelect'] = function(opt) {
    this.each(function(i, element) {
      new LinkSelect(element, opt)
    })

    return this
  }

  return LinkSelect
})