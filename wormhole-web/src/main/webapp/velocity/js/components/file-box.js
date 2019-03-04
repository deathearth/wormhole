define(['lodash', 'jquery'], function(_, $) {

    var FileBox = function ($el) {
      this.$el = $el
      this.init()
    }
    FileBox.prototype.init = function () {
      var $file = this.$el.find(':file')
      var $text = this.$el.find('.file-text')
      var defaultText = $text.text()

      $file.on('change', function () {
        var files = $file.prop('files')
        if (files.length > 0) {
          $text.text(files[0].name)
        } else {
          $text.text(defaultText)
        }
      })
    }
    FileBox.bind = function ($el) {
      return new FileBox($el)
    }
  
    return FileBox
  })