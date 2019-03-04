define(['jquery','lodash', 'tabhelper', 'utils/utils','bootstrap'], function ($,_, tabHelper, utils) {

	var listRender = _.template($('#selectTmpl').html());
	var $list = $('[role=select]');
	var $search = $('[role=search]')
	var loading = function(page) {
		var query = utils.serializeJson($search)
		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/app/proto/index',
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
	
	
	

	var syntaxHighlight = function(json) {
		if (typeof json != 'string') {
			json = JSON.stringify(json, undefined, 2);
		}
		json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
		return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
			var cls = 'number';
			if (/^"/.test(match)) {
				if (/:$/.test(match)) {
					cls = 'key';
				} else {
					cls = 'string';
				}
			} else if (/true|false/.test(match)) {
				cls = 'boolean';
			} else if (/null/.test(match)) {
				cls = 'null';
			}
			return '<span class="' + cls + '">' + match + '</span>';
		});
	}
	
	
    var add = function() {
    	
    	var length = document.getElementsByName("enables").length;
    	length = length + 1;
    	var key = "header_key"+length;
    	var val = "header_value"+length
    	
    	
        $("#requests tr:last").after("<tr>" +  
        		"<td style='width:120px;'><input type='checkbox' name='enables' checked value='"+length+"'><a onclick='$(this).parent().parent().remove()' style='cursor:pointer' >"+"删"+"</a></td>"+ 
			 	"<td><input type='text' class='form-control' name='"+key+"' id='"+key+"' value='' maxlength='20' style='width:200px;height:33px;'></td>"+ 
			 	"<td><input type='text' class='form-control' name='"+val+"' id='"+val+"' value='' maxlength='80' style='width:200px;height:33px;'></td>"+ 
                "</tr>"); 
    	//check=true;
    } ;
    
    
    //增加请求
    $('[role=add_header]').on('click', function () {
		add();
	});
	
	//下拉框change事件
	$('#env').on('change',function(){  
		var val = this.value;
		if(val=="-1"){
			var str = "自定义：<input type='text' class='form-control' name='self' id='self' value='http://127.0.0.1:8080/' maxlength='200' style='width:250px;height:33px;'>";
			$("#zdy").html(str);
		}else{
			$("#zdy").html("");
		}
	});
	
	//请求地址切换
//    $('[role=callback]').on('click', function () {
//    	if($("#callback").is(':checked')) {
//    		var apiName = $("#url").val().split("apiName=")[1].split("&")[0];
//    		$("#url").val("gateway/callback/"+apiName);
//    	 }
//	});
	
	
	$("form[name='base']").on('submit', function(event) {
		var type = $("#type").val();   //请求类型
		var env = $("#env").val();     //环境框
		var self = $("#self").val();   //自定义环境框框
		var url = $("#url").val();     //请求地址
		var body = $("#body").val(); //请求体
		
		if(env==-1 && (self==null || self.indexOf(":")<0)){
			alert("自定义地址输入有误!");
			return false;
		}
		
		if(url==null || (url.indexOf("api")>0 && url.indexOf("&")<0) || (url.indexOf("api")<0 &&url.indexOf("callback")<0)){
			alert("url请求地址有误!");
			return false;
		}
		
        var $form = $(this)
        var data = utils.serializeJson($form)
        $.ajax({
            type : 'post',
            dataType : 'json',
            url : '/gateway/api/tryTest',
            contentType: 'application/json',
            data : JSON.stringify(data),
        }).done(function(result) {
            	//location.href = "#jump";
//        		document.getElementById("response").innerText = syntaxHighlight(result.jsonResp.data);  //html也被处理,不合适
        		document.getElementById("response").innerText = result.jsonResp.data;
        		var signs = result.jsonResp.status=="error"?"":result.jsonResp.status;
            	$("#header_value3").val(signs.split("###")[0]);
            	$("#header_value7").val(signs.split("###")[1]);
        }).fail(function(resp, msg, err) {
            alert(msg)
        })
        return false;
    })
	
	$(function () { $("[data-toggle='tooltip']").tooltip(); });
	
})