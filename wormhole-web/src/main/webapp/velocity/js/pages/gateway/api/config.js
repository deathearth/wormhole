define([ 'jquery','lodash','tabhelper', 'utils/utils','bootstrap'], function($,_, tabHelper, utils) {

	var loading = function() {
		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/api/get/dbtype'
		}).done(function(result) {
				var ds = result.jsonResp.data;
				if(ds=="dataSource"){
					$("#dev").addClass("btn-primary");
					$("#test").removeClass("btn-primary");
				}else{
					$("#test").addClass("btn-primary");
					$("#dev").removeClass("btn-primary");
				}
				$("#type").html(result.jsonResp.data);
		}).fail(function(resp, msg, err) {
			alert(msg)
		})
	}
//	loading();
	
	//数据源
//	$('[role=dev]').on('click', function () {
//		cut(1);
//    })
    
//    $('[role=test]').on('click', function () {
//    	cut(2);
//    })
    
//    $('[role=refresh]').on('click', function () {
//    	refresh();
//    })
    
//    var cut = function(type) {
//		$.ajax({
//			type : 'get',
//            dataType : 'json',
//			url : '/gateway/api/set/dbtype?type='+type
//		}).done(function(result) {
//				//$("#type").html(result.data);
//				loading();
//		}).fail(function(resp, msg, err) {
//			alert(msg)
//		})
//	}
	
//	var refresh = function() {
//		$.ajax({
//			type : 'get',
//            dataType : 'json',
//			url : '/gateway/api/refresh'
//		}).done(function(result) {
//				alert("更新成功!!!");
//		}).fail(function(resp, msg, err) {
//			alert(msg)
//		})
//	}

//    //数据同步
//    $('[role=api]').on('click', function () {
//        sync("api");
//    })
//    $('[role=group]').on('click', function () {
//    	sync("group");
//    })
//    $('[role=app]').on('click', function () {
//    	sync("app");
//    })
//    $('[role=partner]').on('click', function () {
//    	sync("partner");
//    })
//    $('[role=auth]').on('click', function () {
//    	sync("auth");
//    })
//    $('[role=all]').on('click', function () {
//    	sync("all");
//    })
//    //$('[role=copy]').on('click', function () {
//    //	sync("all");
//    //})
//    var sync = function(type) {
//		$.ajax({
//			type : 'get',
//            dataType : 'json',
//			url : '/gateway/api/sync/datainfo?type='+type
//		}).done(function(result) {
//				$("#sql").html(result.jsonResp.data.syncData);
//		}).fail(function(resp, msg, err) {
//			alert(msg)
//		})
//	}
})