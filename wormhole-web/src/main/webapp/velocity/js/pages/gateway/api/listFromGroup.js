define([ 'jquery', 'lodash', 'tabhelper', 'moment', 'utils/utils', 'utils/pagination','bootstrap' ], 
	function($, _, tabHelper, moment, utils, Pagination) {
	
	var listRenderGroup = _.template($('#selectTmpl').html());
	var $listGroup = $('[role=select]');
	var $searchGroup = $('[role=group]')
	var loadingGroup = function(page) {
		var query = utils.serializeJson($searchGroup)
		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/apigroup/proto/index',
			data: query,
		}).done(function(result) {
			if (result.status === 'ok') {
				var markup = listRenderGroup({
					list : result.data
				})
				$listGroup.html(markup)
			} else {
				alert(result.data)
			}
		}).fail(function(resp, msg, err) {
			alert(msg)
		})
	}
	loadingGroup();
	
	for(i=0;i<10000;i++){
		
	}
	
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
			url : '/gateway/api/proto/search',
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
		tabHelper.openChildTab('/gateway/api/add')
	})

	//跳转到编辑页面
	$list.on('click', '[role=edit]', function () {
		var id = $(this).data('id')
		tabHelper.openChildTab('/gateway/api/edit?id=' + id)
	})
	
	//跳转到详情页面
	$list.on('click', '[role=detail]', function () {
		var id = $(this).data('id')
		tabHelper.openChildTab('/gateway/api/detail?id=' + id)
	})
	
	//跳转到测试页面
	$list.on('click', '[role=test]', function () {
		var id = $(this).data('id')
		tabHelper.openChildTab('/gateway/api/testinfo?id=' + id)
	})

	$list.on('click', '[role=delete]', function() {
		var ok = confirm('确认删除该api吗?')
		if (ok) {
			var id = $(this).data('id')
			$.ajax({
				type : 'get',
				dataType : 'json',
				url : '/gateway/api/delete?id='+id,
			}).done(function(result) {
				if (result.jsonResp.status === 'ok') {
					alert("api删除成功!!!");
					pager.reload()
				} else {
					alert(result.data)
				}
			}).fail(function(resp, msg, err) {
				alert(msg)
			})
		}
	})
	
	//同步到线上环境
	$list.on('click', '[role=transfer]', function() {
		var ok = confirm('确认要同步吗?')
		if (ok) {
			var id = $(this).data('id')
			$.ajax({
				type : 'get',
				dataType : 'json',
				url : '/gateway/api/send?id='+id,
			}).done(function(result) {
				if (result.status === 'ok') {
					alert("同步成功!!!");
					pager.reload()
				} else {
					alert(result.data)
				}
			}).fail(function(resp, msg, err) {
				alert(msg)
			})
		}
	})

	//复制数据
	$list.on('click', '[role=copy]', function() {
		var ok = confirm('确认要复制吗?')
		if (ok) {
			var id = $(this).data('id')
			$.ajax({
				type : 'get',
				dataType : 'json',
				url : '/gateway/api/copy?id='+id,
			}).done(function(result) {
				if (result.status === 'ok') {
					alert("复制成功!!!");
					pager.reload()
				} else {
					alert(result.data)
				}
			}).fail(function(resp, msg, err) {
				alert(msg)
			})
		}
	})
	
	
	/** 标签下拉框开始 ***/
	var listRenderSelect = _.template($('#selectOneTmpl').html());
	var $listSelect = $('[role=rootId]');
	var loadingSelect = function(page) {
		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/mark/proto/select/level/one',
		}).done(function(result) {
			if (result.status === 'ok') {
				var markup = listRenderSelect({
					list : result.data
				})
				$listSelect.html(markup)
			} else {
				alert(result.data)
			}
		}).fail(function(resp, msg, err) {
			alert(msg)
		})
	}
	loadingSelect();
	
	$('[role=rootId]').on('change', function() {
    	var html = "<option value='' selected>全部</option>";
    	var val = this.value;
	    	$.ajax({
				type : 'get',
	            dataType : 'json',
				url : '/gateway/mark/proto/select/level/two',
				data: {"rootId":val},
			}).done(function(result) {
				if (result.status === 'ok') {
					var data = result.data;
					for(var i = 0;i<data.length;i++){
						html += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
					}
					$("#branchId").html(html);
				}else{
					$("#branchId").html(html);
				}
			});
    	
    })
    
    $('[role=branchId]').on('change', function() {
    	var html = "<option value='' selected>全部</option>";
    	var rootId = $("#rootId option:selected").val();
    	var val = this.value;
	    	$.ajax({
				type : 'get',
	            dataType : 'json',
				url : '/gateway/mark/proto/select/level/three',
				data: {"rootId":rootId,"branchId":val},
			}).done(function(result) {
				if (result.status === 'ok') {
					var data = result.data;
					for(var i = 0;i<data.length;i++){
						html += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
					}
					$("#leafId").html(html);
				}else{
					$("#leafId").html(html);
				}
			});
    })
	/** 标签下拉框结束 ***/
	
	
	$(function () { $("[data-toggle='tooltip']").tooltip(); });
})