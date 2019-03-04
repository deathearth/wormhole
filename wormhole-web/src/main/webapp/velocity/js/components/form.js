define(['lodash', 'jquery', 'tabhelper', 'utils/utils'], 
function(_, $, tabHelper, utils) {
  var formTypeMap = {
    'json': {
      processData: false,
      contentType: 'application/json'
    },
    'formdata': {
      processData: false
    },
    'urlencoded': {

    }
  }
  var ajaxForm = function ($form, callback, options) {
    options = options || {}
    var url = $form.attr('action')
    var method = $form.attr('method')
    var formtype = $form.attr('formtype')

    $form.on('submit', function (e) {
      e.preventDefault()
      e.stopPropagation()
      options.beforeSubmit && options.beforeSubmit()

      var data = utils.serializeJson($form)
      if (formtype === 'json') {
        data = JSON.stringify(data)
      }
      var ajaxOption = _.assign({
        url: url,
        dataType: 'json',
        type: method,
        data: data
      }, formTypeMap[formtype] || {})
      
      $.ajax(ajaxOption).done(function (result) {
        if (result.status === 'ok') {
          callback && callback(result)
        } else {
          if (options.onFail) {
            options.onFail(result)
          } else {
            alert(result.data)
          }
        }
      }).fail(function (resp, msg, err) {
        if (options.onError) {
          options.onError(resp, msg, err)
        } else {
          alert('网络请求错误')
        }
      })
    })
  }

  return ajaxForm
})