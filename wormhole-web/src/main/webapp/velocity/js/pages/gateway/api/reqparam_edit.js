define(['jquery', 'tabhelper', 'utils/utils'], function ($, tabHelper, utils) {

    $('form').on('submit', function(event) {
        var $form = $(this)
        var data = utils.serializeJson($form)
        $.ajax({
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            url: '/gateway/servicerequest/proto/put',
            data: JSON.stringify(data),
        }).done(function (result) {
            if (result.status === 'ok') {
                alert('保存成功!!!!!!!!')
                tabHelper.close(true)
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
    
	var selectType = function(value){
    	var Str = $('#type option:selected').text();
    	if(value==7){
    		Str = "<input type='text' class='form-control' id='typeName' name='typeName' value='' />";
    	}else{
    		Str = "<input type='text' class='form-control' id='typeName' name='typeName' value='"+Str+"' readOnly/>";
    	}
    	$("#follow").html(Str);
    }
    
  //绑定事件
	$('#type').on('change',function(){  
		selectType(this.value);
	});
	
})