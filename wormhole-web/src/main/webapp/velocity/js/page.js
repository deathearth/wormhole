define(['lodash', 'jquery', 'tabhelper', 'utils/utils', 
'components/form', 'components/img-box', 'components/file-box', 'components/list'], 
function(_, $, tabHelper, utils, ajaxForm, imgbox, filebox, ajaxList) {

  var autoForm = function () {
    /*
    // 自动处理所有标注为auto的表单
    $('form').each(function () {
      var $form = $(this)
      if ($form.attr('auto') !== undefined) {
        ajaxForm($form, function () {
          alert('提交成功')
          tabHelper.close(true)
        })
      }
    })
    */
  }
  var autoList = function () {
    /*
    // 自动处理所有标注为auto的list
    var $list = $('#list')
    if ($list.length > 0 && $list.attr('auto') !== undefined) {
      ajaxList($list)
    }
    */
  }
  // 禁用回车提交表单
  var disableEnter = function () {
    $('form').on('keydown', function (e) {
      if (e.keyCode === 13) {
        return false
      }
    })
  }

  var init = function (title) {
    if (title && title.length > 0) {
      tabHelper.setTitle(title)
    }
    // 标注target=tabpage的a标签打开新标签页
    $('body').on('click', 'a[target=tabpage]', function () {
      tabHelper.openTab($(this).attr('href'))
      return false
    })
    // 标注target=childpage的a标签打开新的子标签页
    $('body').on('click', 'a[target=childpage]', function () {
      tabHelper.openChildTab($(this).attr('href'))
      return false
    })
    // 标注role=closetab的元素关闭当前页
    $('body').on('click', '[role=closetab]', function () {
      tabHelper.close()
      return false
    })
    // 自动配置图片上传&预览控件
    $('[role=imgbox]').each(function () {
      imgbox.bind($(this))
    })
    // 自动配置文件上传控件
    $('[role=filebox]').each(function () {
      filebox.bind($(this))
    })

    autoForm()
    autoList()
    disableEnter()
  }

  return {
    init: init
  }
})
