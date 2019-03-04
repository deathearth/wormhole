define(['jquery', 'tabhelper', 'utils/utils','bootstrap'], function ($, tabHelper, utils) {

    $('form').on('submit', function(event) {
    	var name = $("#name").val();
		if(name==null||name==""){
			alert('APP分组名称不能为空!!!');
			return false;
		}
//		if(name.indexOf("_")<0){
//			alert("APP分组名称请按照'系统/服务名_业务/模块名'的格式命名!");
//			return false;
//		}
//		var apiNames = name.split("_");
//		if(apiNames.length != 2){
//			alert("APP分组名称请按照'系统/服务名_业务/模块名'的格式命名!");
//			return false;
//    	}
		
        var $form = $(this)
        var data = utils.serializeJson($form)
        $.ajax({
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            url: '/gateway/apigroup/proto/put',
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
    
    $(function () { $("[data-toggle='tooltip']").tooltip(); });


})