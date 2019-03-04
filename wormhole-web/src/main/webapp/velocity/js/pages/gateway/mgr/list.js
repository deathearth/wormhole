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
			url : '/gateway/mgr/auth/grant/index',
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
	
	
	
	
	var listRenderTag2 = _.template($('#listTmplTag2').html())
	var $listTag2 = $('[role=listTag2]')
	var loadingTag2 = function(page) {
		var query = utils.serializeJson($search)
		query.pageIndex = page
		query.pageSize = pagerTag2.pageSize
		query.authIdType = 2;
		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/mgr/auth/grant/index',
			data: query,
		}).done(function(result) {
			if (result.status === 'ok') {
				var markup = listRenderTag2({
					list : result.data,
					offset: (page - 1)*pagerTag2.pageSize,
					moment : moment
				})
				//$("#msg").text(result.msg)
				$listTag2.html(markup)
				pagerTag2.set(page, result.itemsCount)
			} else {
				alert(result.data)
			}
		}).fail(function(resp, msg, err) {
			alert(msg)
		})
	}
	//初始化分页控件
	var pagerTag2 = new Pagination({ el: $('#tag2'), loader: loadingTag2 })
	//首次載入初始化
	pagerTag2.goto(2);
	
	
	tabHelper.onChildClose(function(result) {
		if (result.data == true) {
			pager.reload()
		}
	})
  
	$search.on('submit', function () {
		pager.goto(1)
		return false
	})
	
	
	$('#AGROUP').click(function (e) {
		$("#gateway-li-group").addClass("active");
		$("#gateway-li-api").removeClass("active");
		$("#gateway-group").addClass("in active");
		$("#gateway-api").removeClass("in active");
		$("#grantAuthIdType").val(1);
		pager.goto(1);
	})
	
	$('#AAPI').click(function (e) {
		$("#gateway-li-api").addClass("active");
		$("#gateway-li-group").removeClass("active");
		$("#gateway-api").addClass("in active");
		$("#gateway-group").removeClass("in active");
		$("#grantAuthIdType").val(2);
		pagerTag2.goto(1);
	})
	

	
	$list.on('click', '[role=grant]', function() {
		var id = $(this).data('id');
		var name = $(this).data('name');
		_grant(id,name);
	})
	
	$listTag2.on('click', '[role=grant]', function() {
		var id = $(this).data('id');
		var name = $(this).data('name');
		_grant(id,name);
	})
	
	var _grant = function(id, name){
		var ok = confirm('确认增加授权吗?')
		if (ok) {
			$("#grantAuthId").val(id);
			$("#grantAuthName").val(name);
			
			var $grant = $('[role=grant]')
	        var data = utils.serializeJson($grant)
			$.ajax({
				type : 'post',
	            dataType : 'json',
	            url : '/gateway/mgr/grant',
	            contentType: 'application/json',
	            data : JSON.stringify(data),
			}).done(function(result) {
				if (result.status === 'ok') {
					alert("授权成功!!!");
					var type = $("#grantAuthIdType").val();
					if(type==1){
						pager.reload()
					}else{
						pagerTag2.reload()
					}
				} else {
					alert(result.data)
				}
			}).fail(function(resp, msg, err) {
				alert(msg)
			});
		}
	}
	
	
	
	$list.on('click', '[role=ungrant]', function() {
		var id = $(this).data('id');
		var name = $(this).data('name');
		var pk = $(this).data('pk');
		_ungrant(id,name,pk);
	})
	
	$listTag2.on('click', '[role=ungrant]', function() {
		var id = $(this).data('id');
		var name = $(this).data('name');
		var pk = $(this).data('pk');
		_ungrant(id,name,pk);
	})
	
	var _ungrant = function(id,name,pk){
		var ok = confirm('确认解除授权吗?');
		if (ok) {
			$("#grantAuthId").val(id);
			$("#grantAuthName").val(name);
			$("#grantId").val(pk);
			
			var $grant = $('[role=grant]');
	        var data = utils.serializeJson($grant);
			$.ajax({
				type : 'post',
	            dataType : 'json',
	            url : '/gateway/mgr/ungrant',
	            contentType: 'application/json',
	            data : JSON.stringify(data)
			}).done(function(result) {
				if (result.status === 'ok') {
					alert("解除授权成功!!!");
					var type = $("#grantAuthIdType").val();
					if(type==1){
						pager.reload();
					}else{
						pagerTag2.reload();
					}
				} else {
					alert(result.data);
				}
			}).fail(function(resp, msg, err) {
				alert(msg);
			});
		}
	}
})