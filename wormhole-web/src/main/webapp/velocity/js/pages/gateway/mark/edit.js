define([ 'jquery', 'lodash','tabhelper', 'utils/utils','bootstrap'], function($,_, tabHelper, utils) {

	
    $('form').on('submit', function(event) {
        var $form = $(this);
        
        var name = $("#name").val();
		if(name==null||name==""){
			alert('标签名称不能为空!!!');
			return false;
		}
		if(name.length > 20){
			alert("标签名称不能超过20个汉字!");
			return false;
		}
		
		var name = $("#desc").val();
		if(name==null||name==""){
			alert('标签描述不能为空!!!');
			return false;
		}
		
        var data = utils.serializeJson($form)
        $.ajax({
            type : 'post',
            dataType : 'json',
            url : '/gateway/mark/proto/post',
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
    })

    
    $('[role=rootId]').on('change', function() {
    	var html = "<option value='' selected>全部</option>";
    	var val = this.value;
    	if(val > 0){
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
		}else{
			$("#branchId").html(html);
		}
    	
    })
    
//  三级菜单这里先不做处理
//    $('[role=branchId]').on('change', function() {
//    	//var html = "<option value='0' selected>无</option>";
//    	var rootId = $("#rootId option:selected").val();
//    	var val = this.value;
//    	if(val > 0){
//	    	$.ajax({
//				type : 'get',
//	            dataType : 'json',
//				url : '/gateway/mark/proto/select/level/three',
//				data: {"rootId":rootId,"branchId":val},
//			}).done(function(result) {
//				if (result.status === 'ok') {
//					var data = result.data;
//					for(var i = 0;i<data.length;i++){
//						html += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
//					}
//				}
//			});
//		}
//    	$("#leafId").html(html);
//    })
    
    
    
    
    $('[role=cancel]').on('click', function() {
        tabHelper.close(false)
    })

    $(function () { $("[data-toggle='tooltip']").tooltip(); });
})