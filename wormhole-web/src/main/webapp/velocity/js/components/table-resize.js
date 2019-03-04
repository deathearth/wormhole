define(['jquery', 'lodash'], function ($, _) {
  var defaultOpt = {
    minWidth: 40
  }

  var ResizableTable = function (el, opt) {
    this.init(el, opt)
  }
  ResizableTable.prototype.init = function (el, opt) {
    var _this = this
    this.$el = $(el)
    if (!this.$el.is('table')) {
      return
    }

    this.opt = _.assign({}, defaultOpt, opt)
    this.$el.addClass('table-resizable')

    this.$ths = this.$el.find('thead th')
    this.widthList = []
    this.originWidthList = []
    this.fixed = {}

    this.$ths.each(function (index, el) {
      _this.widthList.push(el.clientWidth)
    })
    this.$ths.each(function (index, el) {
      $(el).css({ width: _this.widthList[index] + 'px' })
      if ($(el).attr('fixed') === 'true') {
        _this.fixed[index] = true
      } else if (index < (_this.$ths.length - 1)) {
        _this.setResizer($(el), index)
      }
    })

    var timeout = null
    $(window).on('resize', function () {
      if (timeout) {
        clearTimeout(timeout)
        timeout = null
      }
      timeout = setTimeout(function () {
        _this.rebaseSize()
      }, 125)
    })
  }
  ResizableTable.prototype.setResizer = function ($th, index) {
    var _this = this
    var $table = this.$el
    var $resizer = $('<span class="resizer"></span>')
    var resizing = false
    var $body = $(document.body)

    var startX = 0
    $resizer.on('mousedown', function (e) {
      resizing = true
      startX = e.clientX
      _this.saveOriginSize()
      $table.addClass('resizing')
    })
    $body.on('mouseup', function () {
      if (resizing) {
        $table.removeClass('resizing')
        resizing = false
      }
    })
    $body.on('mousemove', function (e) {
      if (!resizing) { return }
      var diff = e.clientX - startX
      _this.updateSize(index, diff)
    })

    $th.append($resizer)
  }
  // 开始拖拽时保存当前宽度数据
  ResizableTable.prototype.saveOriginSize = function () {
    var _this = this
    _.forEach(this.widthList, function (w, i) {
      _this.originWidthList[i] = w
    })
  }
  // 计算拖拽后的宽度数据
  ResizableTable.prototype.updateSize = function (index, diff) {
    var minDiff = -(this.originWidthList[index] - this.opt.minWidth)
    diff = minDiff > diff ? minDiff : diff

    var originWidth, moveWidth
    var surplus = diff
    var moveList = []

    for (var i = index + 1; i < this.widthList.length; i++) {
      if (this.fixed[i] === true) { continue }
      originWidth = this.originWidthList[i]
      moveWidth = originWidth - this.opt.minWidth
      if (moveWidth > surplus) {
        moveWidth = surplus
      }
      if (moveWidth !== 0) {
        var newWidth = this.originWidthList[i] - moveWidth
        if (newWidth !== this.widthList[i]) {
          moveList.push(i)
          this.widthList[i] = newWidth
        }
        surplus -= moveWidth
      }

      if (surplus === 0) {
        break
      }
    }
    if (surplus !== diff) {
      var newWidth = this.originWidthList[index] + (diff - surplus)
      if (newWidth !== this.widthList[index]) {
        this.widthList[index] = newWidth
        moveList.push(index)
      }
    }

    this.renderSize(moveList)
  }
  // 将宽度改变渲染到元素上
  ResizableTable.prototype.renderSize = function (moveList) {
    var _this = this
    _.forEach(moveList, function (index) {
      _this.$ths.eq(index).css({ width: _this.widthList[index] + 'px' })
    })
  }
  // 窗口宽度变化时重设宽度基数
  ResizableTable.prototype.rebaseSize = function () {
    var _this = this
    var totalW = 0, minW = 0, fixedWidth = 0, resizeCount = 0
    _.forEach(this.widthList, function (w, i) {
      if (_this.fixed[i] === true) { 
        minW += w
        fixedWidth += w
      } else {
        minW += 40
        totalW += w
        resizeCount++
      }
    })
    var currentW = this.$el.parent().width()
    if (currentW < minW) { return }

    currentW -= fixedWidth
    var diff = totalW - currentW
    var scale =  currentW / totalW
    var moveList = []
    _.forEach(this.widthList, function (w, i) {
      if (_this.fixed[i] === true) { return }
      var newWidth = Math.floor(w * scale)
      newWidth = (newWidth < _this.opt.minWidth) ? _this.opt.minWidth : newWidth
      var itemDiff = w - newWidth
      diff -= itemDiff
      _this.widthList[i] = newWidth
      moveList.push(i)
    })
    if (diff !== 0) {
      for (var i = 0; i < this.widthList.length; i++) {
        if (this.fixed[i] === true) { continue }
        var newWidth = this.widthList[i] - diff
        newWidth = (newWidth < this.opt.minWidth) ? this.opt.minWidth : newWidth
        var itemDiff = this.widthList[i] - newWidth
        diff -= itemDiff
        this.widthList[i] = newWidth
        if (diff === 0) {
          break
        }
      }
    }

    this.renderSize(moveList)
  }

  // 创建jquery插件
  $.fn['resizable'] = function (opt) {
    this.each(function (i, element) {
      var r = new ResizableTable(element, opt)
      
    })

    return this
  }
})