// 使用requirejs进行js模块管理
// 全局函数define用来定义一个模块
define(['jquery', 'lodash', 'tabhelper', 'utils/utils', 'utils/pagination'], 
// 第一个数组参数定义了依赖的其他模块，会根据顺序注入到function的参数列表中
// 其中定义了别名的模块可直接写模块名，其他的则需要写路径，路径是相对与velocity文件夹的
// 目前项目定义了别名的模块包括jquery,lodash,bootstrap,moment,config和tabhelper
// 定义了别名的文件夹包括pages和utils
function ($, _, tabHelper, utils, Pagination) {
  // jquery, lodash 的API参考官方文档

  // 获取页面地址上的query参数
  var pageQuery = utils.getPageQuery()
  // 如page?id=1&name=aaa的地址，会得到{ "id":"1", "name":"aaa" }的对象

  // 定义一个请求数据的函数
  var loading = function () {
    var query = utils.serializeJson($search) //将表单数据序列号为一个符合JSON规范的对象
    query = _.assign(query, pageQuery) //将页面地址栏参数合并到formdata对象上

    // 包含二进制文件时
    // var formdata = new FormData($search[0]) // 当form的html结构完全匹配时，可直接初始化
    var formdata = new FormData()
    formdata.append('id', $('[name=id]').val()) // 逐字段加入
    formdata.append('file', $('[name=file]').prop('files')[0])  // 加入上传文件

    // 发起ajax请求
    var xhr = $.ajax({
      url: 'channel/bank',
      type: 'post',
      dataType: 'json',
      processData: false,               // 直接提交json数据，不进行jquery的预处理
      // contentType: false,            // 包含二进制文件提交时，不设置content-type，由浏览器指定
      contentType: 'application/json',  // 手动指定http头的content-type为application/json,大部分时候可省略
      // data: formdata,                // 包含二进制文件时提交FormData对象
      data: JSON.stringify(query)
    }).done(function (result) { // 请求成功
      if (result.status === 'ok') {
        // 渲染页面数据
      } else {
        alert(result.data)
      }
    }).fail(function (response, msg, err) { // 请求时发生错误
      alert(msg)  // 直接弹出请求错误信息
      alert('网络请求错误！')  // 自定义错误信息
    }).always(function () { // 无论成功失败都会调用
      console.log('请求结束')
    })

    // 如果需要取消请求
    if (xhr.state() === 'pending') {  // 只有请求还在pending中才能取消
      xhr.abort()
    }
  }
  // 查询表单 (一般变量命名以$开头表示一个jquery包装的dom对象)
  var $search = $('[role=search]')
  $search.on('submit', function () {
    loading()
    return false  // 必须return false取消掉表单的默认提交事件
  })

  // 使用分页 
  var pager = new Pagination({ el: $('.page-box'), loader: loading })
  var loading = function (page) { // 修改loading方法
    var query = utils.serializeJson($search)
    query.page = page
    query.page_count = pager.pageSize
    // ajax请求不变
  }
  pager.goto(1)  //跳转到第n页
  pager.reload() //刷新当前页
  $search.on('submit', function () {  //修改查询表单提交事件
    pager.goto(1)
    return false  // 必须return false取消掉表单的默认提交事件
  })

  //表单页
  var $form = $('form')
  $form.on('submit', function () {
    var ok = confirm('是否确认提交？')
    if (!ok) {
      return false
    }

    var formdata = utils.serializeJson($form)
    $.ajax({
      url: '/channel/add',
      dateType: 'json',
      type: 'post',
      contentType: 'application/json',
      data: JSON.stringify(formdata)
    }).done(function (result) {
      if (result.status === 'ok') {
        alert('提交成功!')
        tabHelper.close(true)
      } else {
        alert(result.data)
      }
    }).fail(function (resp, msg, err) {
      alert('请求出错！')
    })

    return false
  })

  // tabhelper 用来实现框架tab页功能的辅助库
  tabhelper.setTitle('标题')  // 即时修改tab页标题
  tabhelper.openTab('/channel/list')  // 打开一个新tab页
  tabhelper.openChildTab('/channel/add')  // 打开一个子tab页,子tab页与父tab页强相关，当父tab被关闭时，所有子tab页也会被关闭
  tabhelper.closeChildren() // 关闭当前页的所有子页
  tabhelper.close(true) // 关闭当前页,参数可省略,当有参数时会被父业的onChildClose接收到
  tabhelper.toChildren('aaaa' /* 或{ a: 1, b: 2 } */)  // 向子页发送消息/数据
  tabhelper.toParent('aaaa' /* 或{ a: 1, b: 2 } */)  // 向父页发送消息/数据
  tabhelper.subscribe(function (from, msg) {  // 订阅父、子页发来的消息
    if (from === 'child') { // 来自子页
      console.log(msg)
    } else if (from === 'parent') { // 来自父页
      console.log(msg)  
    }
  })
  tabhelper.onChildClose(function (info) {  // 注册子页调用close关闭时的回调函数
    console.log(info.url) // 被关闭子页的url地址
    console.log(info.data)  // 子页关闭时close方法传入的参数
  })
})