define(['lodash', 'jquery'], function(_, $) {

  var readImg = function (file, callback, errcb) {
    var reader = new FileReader()
    reader.onload = function (evt) {
      callback && callback(evt)
    }
    reader.onerror = function (err) {
      errcb && errcb(err)
    }
    reader.readAsDataURL(file)
  }

  var ImageBox = function ($el) {
    this.$el = $el
    this.init()
  }
  ImageBox.prototype.init = function () {
    var $file = this.$el.find(':file')
    var $thumbnail = this.$el.find('.thumbnail')
    var $img = this.$el.find('img')
    var defaultImg = $img.attr('src')


    var showDefault = function () {
      if (defaultImg && defaultImg.length > 0) {
        $img.prop('src', defaultImg)
      } else {
        $thumbnail.css('visibility', 'hidden')
      }
    }
    var fileChange = function () {
      var files = $file.prop('files')
      if (files && files.length > 0) {
        readImg(files[0], 
          function (evt) {
            $img.prop('src', evt.target.result)
            $thumbnail.css('visibility', 'visible')
          }, 
          function (err) {
            alert(err)
            showDefault()
          })
      } else {
        showDefault()
      }
    }

    $file.on('change', fileChange)
    fileChange()
  }
  ImageBox.bind = function ($el) {
    return new ImageBox($el)
  }

  return ImageBox
})