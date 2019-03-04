define(['jquery', 'lodash'], function ($, _) {
  function parseQuery(queryString) {
    var query = { }
    if (queryString && queryString.length > 0) {
      var pairs = (queryString[0] === '?' ? queryString.substr(1) : queryString).split('&')
      for (var i = 0; i < pairs.length; i++) {
        var pair = pairs[i].split('=')
        query[decodeURIComponent(pair[0])] = decodeURIComponent(pair[1] || '')
      }
    }
    return query
  }

  return {
    getPageQuery: function () {
      return parseQuery(location.search)
    },
    serializeJson: function ($form) {
      var arr = $form.serializeArray()
      var json = {}
      _.each(arr, function (item) {
        if (!json[item.name]) {
          json[item.name] = item.value
        } else {
          if (!(json[item.name] instanceof Array)) {
            json[item.name] = [json[item.name]]
          }
          json[item.name] = json[item.name].concat(item.value)
        }
      })

      return json
    }
  }
})