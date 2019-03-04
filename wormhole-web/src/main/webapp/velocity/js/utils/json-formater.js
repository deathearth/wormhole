define(['lodash', 'jquery'], function(_, jquery) {
  var newId = (function () {
    var __i = 10000
    return function () {
      return (__i++).toString()
    }
  })()

  var formatObj = function (key, data, level) {
    var padding = 25 + 'px'
    var markup = ['<div style="padding-left:' + padding + '">']
    markup.push('<label>' + key + ':</label>')
    if (typeof data === 'string' || typeof data === 'number' || typeof data === 'boolean') {
      markup.push('<span>' + data + '</span>')
    } else if (typeof data === 'undefined' || data === null) {
      markup.push('<span>null</span>')
    } else if (data instanceof Array) {
      var id = newId()
      markup.push('<i class="fa fa-caret-down" data-id="jw' + id + '"></i><div id="jc' + id + '">')
      _.each(data, function (item, i) {
        markup.push(formatObj(i, item, level + 1))
      })
      markup.push('</div>')
    } else {
      var keys = _.keys(data)
      var id = newId()

      markup.push('<i class="fa fa-caret-down" data-id="jw' + id + '"></i><div id="jc' + id + '">')
      _.each(keys, function (key) {
        markup.push(formatObj(key, data[key], level + 1))
      })
      markup.push('</div>')
    }
    markup.push('</div>')
    return markup.join('')
  }
  var JsonFormater = function ($el) {
    this.$el = $el
    this.$el.addClass('json-viewer')
    this.initClick()
  }
  JsonFormater.prototype.initClick = function () {
    this.$el.on('click', 'i.fa', function () {
      var $switch = $(this)
      var id = $switch.data('id').replace('jw', 'jc')
      if ($switch.is('.fa-caret-down')) {
        $switch.removeClass('fa-caret-down').addClass('fa-caret-right')
        $('#' + id).hide()
      } else {
        $switch.removeClass('fa-caret-right').addClass('fa-caret-down')
        $('#' + id).show()
      }
    })
  }
  JsonFormater.prototype.set = function (jsondata) {
    var keys = _.keys(jsondata)
    var markup = _.map(keys, function(key) {
      return formatObj(key, jsondata[key], 0)
    })
    this.$el.html(markup.join(''))
  }

  
  $(function () {
    var $jsonviewer = $('[role=jsonviewer]')
    $jsonviewer.each(function () {
      var $el = $(this)
      var jsonStr = $el.html()
      try {
        var data = JSON.parse(jsonStr)
        new JsonFormater($el).set(data)
      } catch (ex) {
        $el.html(jsonStr)
      }
    })
  })
})

