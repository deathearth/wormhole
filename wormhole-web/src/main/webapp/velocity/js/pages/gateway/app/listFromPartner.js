define([ 'jquery', 'lodash', 'tabhelper', 'moment', 'utils/utils', 'utils/pagination' ], 
	function($, _, tabHelper, moment, utils, Pagination) {
	var listRender = _.template($('#listTmpl').html())
	var $list = $('[role=list]')
	var $search = $('[role=search]')
	
	var loading = function(page) {
		var query = utils.serializeJson($search)
		query.pageIndex = page
		query.pageSize = pager.pageSize

		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/app/proto/index',
			data: query,
		}).done(function(result) {
			if (result.status === 'ok') {
				var markup = listRender({
					list : result.data,
					offset: (page - 1)*pager.pageSize,
					moment : moment
				})
				$("#msg").text(result.msg)
				$list.html(markup)
				pager.set(page, result.itemsCount)
			} else {
				alert(result.data)
			}
		}).fail(function(resp, msg, err) {
			alert(msg)
		})
	}
	//初始化分页控件
	var pager = new Pagination({ el: $('.page-box'), loader: loading })
	//首次載入初始化
	pager.goto(1);
	
	tabHelper.onChildClose(function(result) {
		if (result.data == true) {
			pager.reload()
		}
	})
  
	$search.on('submit', function () {
		pager.goto(1)
		return false
	})

	$('[role=add]').on('click', function() {
		tabHelper.openChildTab('/gateway/app/add')
	})

	//跳转到编辑页面
	$list.on('click', '[role=edit]', function () {
		var id = $(this).data('id')
		tabHelper.openChildTab('/gateway/app/edit?id=' + id)
	})
	
	//跳转到编辑页面
	$list.on('click', '[role=authorize]', function () {
		var id = $(this).data('id')
		tabHelper.openChildTab('/gateway/app/authorize?id=' + id)
	})

	$list.on('click', '[role=delete]', function() {
		var ok = confirm('确认删除吗?')
		if (ok) {
			var id = $(this).data('id')
			$.ajax({
				type : 'get',
				dataType : 'json',
				url : '/gateway/app/delete?id='+id,
			}).done(function(result) {
				if (result.jsonResp.status === 'ok') {
					alert("应用删除成功!!!");
					pager.reload()
				} else {
					alert(result.jsonResp.data)
				}
			}).fail(function(resp, msg, err) {
				alert(msg)
			})
		}
	})

})