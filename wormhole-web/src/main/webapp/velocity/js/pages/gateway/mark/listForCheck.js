define([ 'jquery', 'lodash', 'tabhelper', 'moment', 'utils/utils', 'utils/pagination' ], 
	function($, _, tabHelper, moment, utils, Pagination) {
	
	var listRender = _.template($('#listTmpl').html())
	var $list = $('[role=list]')
	var $search = $('[role=search]')
	var loading = function(page) {
		var query = utils.serializeJson($search)
		query.skip = page
		query.size = pager.pageSize
		$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/mark/proto/check/list',
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
	
	tabHelper.onChildClose(function(result) {
		if (result.data == true) {
			pager.reload()
		}
	})
  
	$search.on('submit', function () {
		pager.goto(1)
		return false
	})
	
	
	$("form[name='save']").on('submit', function(event) {
        var $form = $(this);
        var count = $("input[type='checkbox']").length;
        
        if(count <= 0){
        	alert('暂时没有数据!');
        }
        
        var checkStr = "";
        var idStr = "";
        $.each($('input:checkbox'),function(){
            if(this.checked){
            	checkStr+=$(this).val() + ",";
            }else{
            	idStr += $(this).val() + ",";
            }
        });
        
        var apiId = document.getElementById("apiId").value;
        
        $.ajax({
            type : 'get',
            dataType : 'json',
            url : '/gateway/mark/relationship',
            contentType: 'application/json',
            data : {"checks":checkStr,"ids":idStr,"apiId":apiId},
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
				pager.goto(1);
			}else{
				$("#branchId").html(html);
			}
		});
    })
    
    $('[role=branchId]').on('change', function() {
    	var html = "<option value='' selected>全部</option>";
    	var rootId = $("#rootId option:selected").val();
    	var val = this.value;
    	$.ajax({
			type : 'get',
            dataType : 'json',
			url : '/gateway/mark/proto/select/level/three',
			data: {"rootId":rootId,"branchId":val},
		}).done(function(result) {
			if (result.status === 'ok') {
				var data = result.data;
				for(var i = 0;i<data.length;i++){
					html += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
				}
				$("#leafId").html(html);
				pager.goto(1);
			}else{
				$("#leafId").html(html);
			}
		});
    })
    
})