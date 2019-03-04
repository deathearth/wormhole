define(['jquery', 'tabhelper', 'utils/utils','bootstrap'], function ($, tabHelper, utils) {

	var listRender = _.template($('#selectTmpl').html());
	var $list = $('[role=select]');
	var $search = $('[role=search]')
	var loading = function(page) {
		var query = utils.serializeJson($search)
		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/partner/proto/index',
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
	
    $('form').on('submit', function(event) {
        var $form = $(this)
        
        var name = $("#name").val();
		if(name==null||name==""){
			alert('APP名称不能为空!!!');
			return false;
		}
		
		if(name.indexOf("_")<0){
			alert("apiName请按照'终端_APP名'的格式命名!");
			return false;
		}
		var apiNames = name.split("_");
		if(apiNames.length != 2){
			alert("apiName请按照'终端_APP名'的格式命名!");
			return false;
    	}
        
        
        var data = utils.serializeJson($form)
        $.ajax({
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            url: '/gateway/app/proto/put',
            data: JSON.stringify(data),
        }).done(function (result) {
            if (result.status === 'ok') {
                alert('保存成功!')
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