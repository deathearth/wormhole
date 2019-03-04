define(['jquery','lodash', 'tabhelper', 'utils/utils','bootstrap'], function ($,_, tabHelper, utils) {

	var listRenderGroup = _.template($('#selectTmpl').html());
	var $listGroup = $('[role=select]');
	//var $search = $('[role=search]')
	var loadingGroup = function(page) {
		//var query = utils.serializeJson($search)
		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/apigroup/proto/index'
			//data: query,
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
	
    tabHelper.onChildClose(function(result) {
		if (result.data == true) {
			loading();
		}
	})
    
    var listRender = _.template($('#apilistTmpl').html())
    var $list = $('[role=apilist]')
    var $search = $('[role=search]')
    
    var loading = function() {
		var query = utils.serializeJson($search)
		//query.pageIndex = page
		//query.pageSize = pager.pageSize

		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/servicerequest/proto/index',
			data: query,
		}).done(function(result) {
			if (result.status === 'ok') {
				var markup = listRender({
					list : result.data
				})
				$("#msg").text(result.msg)
				$list.html(markup)
			//	pager.set(page, result.itemsCount)
			} else {
				alert(result.data)
			}
		}).fail(function(resp, msg, err) {
			alert(msg)
		})
	}
    loading();
	
	$(function () { $("[data-toggle='tooltip']").tooltip(); });
	
})