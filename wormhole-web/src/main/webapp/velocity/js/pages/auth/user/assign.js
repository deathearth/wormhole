define([ 'jquery', 'lodash', 'tabhelper', 'moment', 'utils/utils', 'utils/pagination' ],
    function($, _, tabHelper, moment, utils, Pagination) {
        var listRender = _.template($('#listTmpl').html())
        var $list = $('[role=list]')
        var $search = $('[role=search]')
        var userId = utils.getPageQuery().id
        var loading = function() {
            var query = utils.serializeJson($search)
            query.pageIndex = 1
            query.pageSize = 1000

            $.ajax({
                type : 'get',
                dataType : 'json',
                url : '/auth/role/proto/index',
                data: query,
                async: false
            }).done(function(result) {
                if (result.status === 'ok') {
                    var markup = listRender({
                        list : result.data,
                    })
                    $("#msg").text(result.msg)
                    $list.html(markup)
                } else {
                    alert(result.data)
                }
            }).fail(function(resp, msg, err) {
                alert(msg)
            })

            $.ajax({
                type : 'get',
                dataType : 'json',
                url : '/auth/authorization/get?userId='+userId,
            }).done(function(result) {
                if (result.status === 'ok') {
                    $(result.data).each(function (index, obj) {
                        $('#ids_'+obj.roleId).attr("checked","checked")
                    })
                } else {
                    alert(result.data)
                }
            }).fail(function(resp, msg, err) {
                alert(msg)
            })
        }

        loading();

        $search.on('submit', function () {
            var ids = $('[name=ids]').filter(function () { return $(this).prop('checked') }).map(function () { return $(this).val() }).toArray().join('-');

            var query = utils.serializeJson($search)
            query.userId = userId;
            query.roleIds = ids;
            $.ajax({
                type : 'get',
                dataType : 'json',
                url : '/auth/authorization/authz',
                data: query,
                async: true
            }).done(function(result) {
                if (result.status === 'ok') {
                    alert("保存成功");
                } else {
                    alert(result.data)
                }
            }).fail(function(resp, msg, err) {
                alert(msg)
            })
            return false
        })

        $search.on('cancel', function () {
            tabHelper.close(false)
        })
    })