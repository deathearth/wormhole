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
	
	
	var loadingMarks = function() {
		var id = $("#id").val();
		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/api/marks',
			data: {"id":id},
		}).done(function(result) {
			if (result.jsonResp.status === 'ok') {
				$("#marks").html(result.jsonResp.data);
			}
		}).fail(function(resp, msg, err) {
			alert(msg)
		})
	}
	loadingMarks();
	
	tabHelper.onChildClose(function(result) {
		if (result.data == true) {
			loadingMarks();
		}
	})
	
	$('#special1').click(function (e) {
		$('#block1').css('display','block');
		$('#block2').css('display','block');
		$('#block3').css('display','block');
		$('#block4').css('display','block');
		$('#block7').css('display','block');
		document.getElementById("http_method1").checked = true;
	})
	
	$('#special2').click(function (e) {
		$('#block1').css('display','none');
		$('#block2').css('display','none');
		$('#block3').css('display','none');
		$('#block4').css('display','none');
		$('#block7').css('display','none');
	})
	
	$('#special3').click(function (e) {
		$('#block1').css('display','none');
		$('#block2').css('display','none');
		$('#block3').css('display','none');
		$('#block4').css('display','none');
		$('#block7').css('display','none');
	})
	
	
	$('#special4').click(function (e) {
		$('#block1').css('display','none');
		$('#block2').css('display','none');
		$('#block3').css('display','none');
		$('#block4').css('display','none');
		$('#block7').css('display','none');
	})
	
	
    $("form[name='base']").on('submit', function(event) {
    	
    	var radioVal = $('input:radio[name="special"]:checked').val();
    	var rows = $("#requests").find("tr").length;
    	if(rows > 2 && radioVal != 0){ //如果行数正好两行 且 接口类型不是普通类型
    		alert("该接口类型只能添加一条请求参数!!!");
    		return false;
    	}
    	
    	var name = $("#name").val();
		if(name==null||name==""){
			alert('API名称不能为空!!!');
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
		
		if(name.indexOf(".")<0){
			alert("apiName请按照'业务类型.方法名'的格式命名!");
			return false;
		}
		
		var description = $("#description").val();
		if(description==null||description==""){
			alert('API描述不能为空!!!');
			return false;
		}
		
		var authVersion = $("#authVersion").val();
		if(authVersion!=null&&authVersion!=""){
			var re = /^[1-9]\d*$/;
			if(!re.test(authVersion)){
				alert('鉴权协议版本必须是正整数!!!');
				return false;
			}
		}
    	
        var $form = $(this)
        var data = utils.serializeJson($form)
        $.ajax({
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            url: '/gateway/api/proto/put',
            data: JSON.stringify(data),
        }).done(function (result) {
            if (result.status === 'ok') {
                alert('基本信息保存成功!')
                //tabHelper.close(true)
            } else {
                alert(result.data)
            }
        }).fail(function (resp, msg, err) {
            alert(msg)
        })

        return false
    })

    $('[role=example]').on('click', function () {
    	var example = $("#example").val();
		if(example==null||example==""){
			alert('结果示例不能为空!!!');
			return false;
		}
		
		var resultDesc = $("#resultDesc").val();
		if(resultDesc==null||resultDesc==""){
			alert('响应参数不能为空!!!');
			return false;
		}
		
		var codeDesc = $("#codeDesc").val();
		if(codeDesc==null||codeDesc==""){
			alert('响应码不能为空!!!');
			return false;
		}
    	var apiId = $("#apiId").val();
//        var data = utils.serializeJson(formData);
        $.ajax({
            type: 'get',
            dataType: 'json',
            url: '/gateway/apiresult/proto/newput',
            data: {"example":example,"resultDesc":resultDesc,"codeDesc":codeDesc,"apiId":apiId,"version":1},
        }).done(function (result) {
            if (result.status === 'ok') {
                alert('result信息保存成功!')
                //tabHelper.close(true)
            } else {
                alert(result.data)
            }
        }).fail(function (resp, msg, err) {
            alert(msg)
        })

        return false
    })
    
    $('[role=cancel]').on('click', function () {
        tabHelper.close(false)
    })
    
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
			} else {
				alert(result.data)
			}
		}).fail(function(resp, msg, err) {
			alert(msg)
		})
	}
    loading();
    
    var check = false;
    
    var selectType = function(value,id){
    	var Str = $('#type option:selected').text();
    	if(value==7){
    		Str = "<input type='text' id='typeName' name='typeName' value='' style='width:180px;' />";
    	}else{
    		Str = "<input type='text' id='typeName' name='typeName' value='"+Str+"' style='width:180px;' readOnly/>";
    	}
    	$("#follow").html(Str);
    	if(value == 7){ //输入框变化
    		Str = "<textarea class='form-control' rows='10' id='Seexample' name='example' style='width:250px;' ></textarea>";
    		$("#input_1").html(Str);
    		Str = "<textarea class='form-control' rows='10' id='Sedescription' name='description' style='width:250px;' ></textarea>";
    		$("#input_2").html(Str);
    	}
    }
    
    var selectDetailType = function(value){
		var type = $("#type").val();
		var str = "";
		if(value==1){ //基本类型
			switch(type){
				case "2": str="int";break;
				case "3": str="long";break;
				//case "4": str="bool";break;
				case "5": str="float";break;
				case "6": str="double";break;
				default:'--';
			}
		}else{ //引用类型
			switch(type){
				case "2": str="java.lang.Integer";break;
				case "3": str="java.lang.Long";break;
				//case "4": str="java.lang.Boolean";break;
				case "5": str="java.lang.Float";break;
				case "6": str="java.lang.Double";break;
				default:'--';
			}
		}
		str = "<input type='text' id='typeName' name='typeName' value='"+str+"' style='width:100px;' readOnly/>";
		$("#follow").html(str);
    }
    
    var resetR = function(){
    	check = false; 
    }
    
    var add = function() {
    	if(check){
    		alert("一次只能添加一条请求参数!!!");
    		return false;
    	}
    	
    	var radioVal = $('input:radio[name="special"]:checked').val();
    	var rows = $("#requests").find("tr").length;
    	if(rows >= 2 && radioVal != 0){ //如果行数正好两行 且 接口类型不是普通类型
    		alert("该接口类型只能添加一条请求参数!!!");
    		return false;
    	}
    	
    	if(radioVal == 0){
	        $("#requests tr:last").after("<tr>" +  
	                "<td><input type='text' id='index' name='index' value='"+(rows-1)+"' style='width:40px;' readOnly />"+"</td>"+  
	                "<td><input type='text' id='name' name='name' value='' style='width:80px;' />"+"</td>"+  
	                "<td>" +
	                "<select name='type' id='type' style='width:120px;' >"  +
	                "<option value='1'>java.lang.String</option>" +
	                "<option value='2'>int</option>" +
	                "<option value='2'>java.lang.Integer</option>" +
	                "<option value='3'>long</option>" +
	                "<option value='3'>java.lang.Long</option>" +
	                //"<option value='4'>boolean</option>" +
	                //"<option value='4'>java.lang.Boolean</option>" +
	                "<option value='5'>float</option>" +
	                "<option value='5'>java.lang.Float</option>" +
	                "<option value='6'>double</option>" +
	                "<option value='6'>java.lang.Double</option>" +
	                "<option value='7'>自定义类型</option>" +
	                "</select>" +
	                "</td>" +  
	                "<td id='follow'><input type='text' value='java.lang.String' id='typeName' name='typeName' readOnly/>"  +"</td>" +  
	                "<td>" +
	                "<input type='radio' name='isRequired' id='isRequired1' value=0 > 否" +
	                "<input type='radio' name='isRequired' id='isRequired2' value=1 checked> 是" +
	                "</td>" +  
	                "<td id='input_1'><input type='text' id='Seexample' name='example' style='width:80px;' />"  +"</td>" +  
	                "<td id='input_2'><input type='text' id='Sedescription' name='description' style='width:80px;' />"   +"</td>" +  
	                "<td>--"   +"</td>"+  
	                "<td>"+  
	                	"<input type='submit' class='btn btn-primary btn-xs' id='saveR' >"+  
	                	"<a  id='deleteR' name='deleteR'>"+"删除"+"</a>"+  
	                "</td>"+  
	                "</tr>"); 
    	}else if(radioVal == 1){
    		$("#requests tr:last").after("<tr>" +  
	                "<td><input type='text' id='index' name='index' value='0' style='width:40px;' readOnly/>"+"</td>"+  
	                "<td><input type='text' id='name' name='name' value='String' style='width:80px;' readOnly/>"+"</td>"+  
	                "<td><select name='type' id='type' style='width:80px;' ><option value='1'>String</option></select></td>" +  
	                "<td id='follow'><input type='text' value='java.lang.String' id='typeName' name='typeName' readOnly/></td>" +  
	                "<td><input type='radio' name='isRequired' id='isRequired1' value=0 readOnly> 否" +
	                "<input type='radio' name='isRequired' id='isRequired2' value=1 checked readOnly> 是</td>" +  
	                "<td><textarea class='form-control' rows='10' id='Seexample' name='example' style='width:250px;' ></textarea>"  +"</td>" +  
	                "<td><textarea class='form-control' rows='10' id='Sedescription' name='description' style='width:250px;' ></textarea>"   +"</td>" +  
	                "<td>--"   +"</td>"+  
	                "<td><input type='submit' class='btn btn-primary btn-xs' id='saveR' ><a  id='deleteR' name='deleteR'>"+"删除"+"</a></td>"+  
	                "</tr>"); 
    	}else if(radioVal == 2){
    		$("#requests tr:last").after("<tr>" +  
	                "<td><input type='text' id='index' name='index' value='0' style='width:40px;' readOnly/>"+"</td>"+  
	                "<td><input type='text' id='name' name='name' value='maps' style='width:80px;' readOnly/>"+"</td>"+  
	                "<td><select name='type' id='type' style='width:80px;' ><option value='1'>String</option></select></td>" +  
	                "<td id='follow'><input type='text' value='java.util.Map' id='typeName' name='typeName' readOnly/></td>" +  
	                "<td><input type='radio' name='isRequired' id='isRequired1' value=0 readOnly> 否" +
	                "<input type='radio' name='isRequired' id='isRequired2' value=1 checked readOnly> 是</td>" +  
	                "<td><textarea class='form-control' rows='10' id='Seexample' name='example' style='width:250px;' ></textarea>"  +"</td>" +  
	                "<td><textarea class='form-control' rows='10' id='Sedescription' name='description' style='width:250px;' ></textarea>"   +"</td>" +  
	                "<td>--"   +"</td>"+  
	                "<td><input type='submit' class='btn btn-primary btn-xs' id='saveR' ><a  id='deleteR' name='deleteR'>"+"删除"+"</a></td>"+  
	                "</tr>"); 
    	}else if(radioVal == 3){
    		$("#requests tr:last").after("<tr>" +  
	                "<td><input type='text' id='index' name='index' value='0' style='width:40px;' readOnly/>"+"</td>"+  
	                "<td><input type='text' id='name' name='name' value='maps' style='width:80px;' readOnly/>"+"</td>"+  
	                "<td><select name='type' id='type' style='width:80px;' ><option value='7'>自定义</option></select></td>" +  
	                "<td id='follow'><input type='text' value='java.util.Map' id='typeName' name='typeName' readOnly/></td>" +  
	                "<td><input type='radio' name='isRequired' id='isRequired1' value=0 readOnly> 否" +
	                "<input type='radio' name='isRequired' id='isRequired2' value=1 checked readOnly> 是</td>" +  
	                "<td><textarea class='form-control' rows='10' id='Seexample' name='example' style='width:250px;' ></textarea>"  +"</td>" +  
	                "<td><textarea class='form-control' rows='10' id='Sedescription' name='description' style='width:250px;' ></textarea>"   +"</td>" +  
	                "<td>--"   +"</td>"+  
	                "<td><input type='submit' class='btn btn-primary btn-xs' id='saveR' ><a  id='deleteR' name='deleteR'>"+"删除"+"</a></td>"+  
	                "</tr>"); 
    	}
        
        //绑定事件
    	$('#type').on('change',function(){  
    		selectType(this.value,this.id);
    	});
    	
    	//绑定事件
    	$('#deleteR').on('click',function(){  
    		$(this).parent().parent().remove();
    		resetR();
    	});
    	check=true;
    } ;
    
    //增加请求
    $('[role=addRequest]').on('click', function () {
		add();
	})
	
	$("form[name='request']").on('submit', function(event) {
		var index = $("#index").val();
		if(index==null||index==""){
			alert('参数序号不能为空!!!');
			return false;
		}
		var name = $("#name").val();
		if(name==null||name==""){
			alert('参数名称不能为空!!!');
			return false;
		}
		var typename = $("#typeName").val();
		if(typename==null||typename==""){
			alert('参数类型名称不能为空!!!');
			return false;
		}
		var example = $("#Seexample").val();
		if(example==null||example==""){
			alert('参数示例不能为空!!!');
			return false;
		}
		var description = $("#Sedescription").val();
		if(description==null||description==""){
			alert('参数描述不能为空!!!');
			return false;
		}
		
        var $form = $(this)
        var data = utils.serializeJson($form)
        $.ajax({
            type : 'post',
            dataType : 'json',
            url : '/gateway/servicerequest/proto/post',
            contentType: 'application/json',
            data : JSON.stringify(data),
        }).done(function(result) {
            if (result.status === 'ok') {
                alert('保存成功!');
                check=false;
                $("#requests tr:last").remove();
                loading();
                //tabHelper.close(true)
            } else {
                alert(result.data)
            }
        }).fail(function(resp, msg, err) {
            alert(msg)
        })

        return false
    })
    
    //跳转到编辑页面
	$list.on('click', '[role=edit]', function () {
		var id = $(this).data('id')
		tabHelper.openChildTab('/gateway/servicerequest/edit?id=' + id)
	})

	$list.on('click', '[role=delete]', function() {
		var ok = confirm('确认删除吗?')
		if (ok) {
			var id = $(this).data('id')
			$.ajax({
				type : 'get',
				dataType : 'json',
				url : '/gateway/servicerequest/delete?id='+id,
			}).done(function(result) {
				if (result.jsonResp.status === 'ok') {
					loading();
				} else {
					alert(result.jsonResp.data)
				}
			}).fail(function(resp, msg, err) {
				alert(msg)
			})
		}
	})
	
	
	
	$('[role=link]').on('click', function() {
    	var id = $("#id").val();
    	tabHelper.openChildTab('/gateway/mark/check?apiId='+id);
    })
	
	$(function () { $("[data-toggle='tooltip']").tooltip(); });
	
})