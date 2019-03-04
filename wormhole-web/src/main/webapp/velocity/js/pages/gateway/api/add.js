define([ 'jquery','lodash','tabhelper', 'utils/utils','bootstrap'], function($,_, tabHelper, utils) {

	var listRender = _.template($('#selectTmpl').html());
	var $list = $('[role=select]');
	var $search = $('[role=group]')
	var loading = function(page) {
		var query = utils.serializeJson($search)
		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/apigroup/proto/index',
			data: query,
		}).done(function(result) {
			if (result.status === 'ok') {
				var markup = listRender({
					list : result.data
				})
				$list.html(markup)
			} else {
				alert(result.data)
			}
		}).fail(function(resp, msg, err) {
			alert(msg)
		})
	}
	loading();
	
	var $auths = $('[role=auth]');
	$.ajax({
		type : 'get',
        dataType : 'json',
		url : '/gateway/api/get/auths'
	}).done(function(result) {
		if (result.status === 'ok') {
			$auths.html(result.data)
		} else {
			alert(result.data)
		}
	}).fail(function(resp, msg, err) {
		alert(msg)
	})
	
    $('form').on('submit', function(event) {
    	var name = $("#name").val();
		if(name==null||name==""){
			alert('API名称不能为空!!!');
			return false;
		}
		
		if(name.indexOf(".")<0){
			alert("apiName请按照'业务类型.方法名'的格式命名!");
			return false;
		}
		
		var re = /[A-Z]/;
		if(re.test(name)){
			alert('apiName接口名称必须全部是小写!!!');
			return false;
		}
		
		if(name.indexOf("service")>0){
			alert("apiName不能出现service关键字!");
			return false;
		}
		
		var description = $("#description").val();
		if(description==null||description==""){
			alert('API描述不能为空!!!');
			return false;
		}
		
		var authVersion = $("#authVersion").val();
		if(authVersion!=null&&authVersion!=""){
			var re = /^[1-9]\d*$/ ;
			if(!re.test(authVersion)){
				alert('鉴权协议版本必须是正整数!!!');
				return false;
			}
		}
		
		
        var $form = $(this)
        var data = utils.serializeJson($form)
        $.ajax({
            type : 'post',
            dataType : 'json',
            url : '/gateway/api/proto/post',
            contentType: 'application/json',
            data : JSON.stringify(data),
        }).done(function(result) {
            if (result.status === 'ok') {
                alert('保存成功!')
                tabHelper.close(true)
            } else {
                alert(result.data)
            }
        }).fail(function(resp, msg, err) {
            alert(msg)
        })

        return false
    });
    
    
    $('#special1').click(function (e) {
		$('#block1').css('display','block');
		$('#block2').css('display','block');
		$('#block3').css('display','block');
		$('#block4').css('display','block');
		$('#block5').css('display','none');
		document.getElementById("http_method1").checked = true;
	});
	
	$('#special2').click(function (e) {
		$('#block1').css('display','none');
		$('#block2').css('display','none');
		$('#block3').css('display','none');
		$('#block4').css('display','none');
		$('#block5').css('display','none');
		document.getElementById("http_method2").checked = true;
		document.getElementById("is_auth2").checked = true;
		document.getElementById("is_login2").checked = true;
	});
	
	$('#special3').click(function (e) {
		$('#block1').css('display','none');
		$('#block2').css('display','none');
		$('#block3').css('display','none');
		$('#block4').css('display','none');
		$('#block5').css('display','none');
		document.getElementById("http_method2").checked = true;
		document.getElementById("is_auth2").checked = true;
		document.getElementById("is_login2").checked = true;
	});
	
	$('#special4').click(function (e) {
		$('#block2').css('display','none');
		$('#block3').css('display','none');
		$('#block4').css('display','none');
		$('#block5').css('display','block');
		document.getElementById("http_method2").checked = true;
		document.getElementById("is_auth2").checked = true;
		document.getElementById("is_login2").checked = true;
	});

    $('[role=cancel]').on('click', function() {
        tabHelper.close(false)
    })
    
    $(function () { $("[data-toggle='tooltip']").tooltip(); });

})