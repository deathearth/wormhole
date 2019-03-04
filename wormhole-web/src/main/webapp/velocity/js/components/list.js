define(['lodash', 'jquery', 'tabhelper', 'utils/utils', 'components/form', 'utils/pagination', 'moment'], 
function(_, $, tabHelper, utils, ajaxForm, Pagination, moment) {
  var ajaxList = function ($list) {
    var templateSelector = $list.attr('template')
    var searchSelector = $list.attr('search')
    var pagerSelector = $list.attr('pagination')
    
    var $search = $(searchSelector)
    var tmplRender = _.template($(templateSelector).html())
    var pager
    if (pagerSelector) {
      var $pg = $('<input name="page" type="hidden">')
      var $pc = $('<input name="page_count" type="hidden">')

      pager = new Pagination({ 
        el: $(pagerSelector), 
        loader: function (page) {
          $pg.val(page)
          $search.submit()
          $pg.val(1)
        } 
      })

      $pc.val(pager.pageSize)
      $search.append($pg)
      $search.append($pc)
    }

    ajaxForm($(searchSelector), function (result) {
      var markup = tmplRender({
        list: result.data,
        moment: moment
      })
      $list.html(markup)
      if (pager) {
        pager.set(result.currPage, result.totalCount)
      }
    })

    var reload = function () {
      if (pager) {
        pager.reload()
      } else {
        $search.submit()
      }
    }
    tabHelper.onChildClose(function (info) {
      if (info.data === true) {
        reload()
      }
    })

    $list.on('reload', function () {
      reload()
    })

    reload()
  }

  return ajaxList
})