define(['lodash', 'jquery'], function (_, $) {
  var TableEllipsis = function (el) {
    this.init(el)
  }
  TableEllipsis.prototype.init = function (el) {
    this.$table = $(el)
    var $table = this.$table
    if ($table.prop('tagName') !== 'TABLE') { return }

    $table.addClass('table-ellipsis')
    this.$caption = $('<caption class="tip-pack"><div class="tip"></div><div class="checker"></div></caption>')
    this.$tip = this.$caption.find('.tip')
    this.$checker = this.$caption.find('.checker')
    $table.append(this.$caption)

    this.bindEvent()
  }
  TableEllipsis.prototype.bindEvent = function () {
    var _this = this
    var $table = this.$table
    var $tip = this.$tip
    var $checker = this.$checker

    $table.on('mouseenter', 'td', function () {
      var $td = $(this)
      var text = $td.text()
      if (!text || text.trim().length === 0) { return }
      var cWidth = $checker.text(text).show().width()
      $checker.hide()
      if (cWidth <= $td.width()) {
        return
      }

      $tip.text(text).show()
      _this.position($table, $td, $tip)
    })
    $table.on('mouseleave', 'td', function () {
      $tip.hide()
    })
  }
  TableEllipsis.prototype.position = function ($table, $td, $tip) {
    $tip.css({ left: '0', top: '0' }) // 位置重置以准确计算尺寸

    var pos = $td.position()
    var left = pos.left + 8
    var top = pos.top + $td.outerHeight() + 8
    var width = $tip.outerWidth()
    var height = $tip.outerHeight()

    var tWidth = $table.width()
    if (left + width > $table.width()) {
      $tip.addClass('right')
      left = pos.left + $td.outerWidth() - width - 8
    } else {
      $tip.removeClass('right')
    }

    var tHeight = $table.height()
    if (height + top > tHeight) {
      $tip.addClass('bottom')
      top = pos.top - height - 6
    }else {
      $tip.removeClass('bottom')
    }

    $tip.css({ left: left + 'px', top: top + 'px' })
  }

  // 创建jquery插件
  $.fn['convertToEllipsis'] = function () {
    this.each(function (i, element) {
      new TableEllipsis(element)
    })

    return this
  }

  return TableEllipsis
})