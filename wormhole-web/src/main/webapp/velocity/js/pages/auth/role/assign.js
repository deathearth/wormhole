// 资源数据
var resources = []
// 选中情况
var permission = []
define(['jquery', 'lodash', 'tabhelper', 'utils/utils', 'ztree'],
    function ($, _, tabHelper, utils) {
        // 树配置项
        var setting = {
            check: {
                enable: true,
                chkboxType: { "Y": "ps", "N": "s" }
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: 'id',
                    pIdKey: 'pid'
                }
            }
        }
        // 生成树
        var treeObj = $.fn.zTree.init($("#tree"), setting, resources)
        var initTree = function(){
            // 处理数据，配置勾选情况
            _.forEach(resources, function (r) {
                var isChekced = !!_.find(permission, function (pm) {
                    return pm.resId === r.id
                })
                r.checked = isChekced
            })
            // 生成树
            var treeObj = $.fn.zTree.init($("#tree"), setting, resources)
        }

        // 提交按钮
        $('[role=submit]').on('click', function () {
            // 获取所有节点
            var nodes = treeObj.transformToArray(treeObj.getNodes())
            // 筛选选中节点
            var checkedNodes = _.filter(nodes, function (node) {
                return node.checked
            })
            // 提取id转换数据格式
            var submitData = _.map(checkedNodes, function (node) { return node.id }).join('-')
            var roleId = utils.getPageQuery().id
            $.ajax({
                type : 'get',
                dataType : 'json',
                contentType: 'application/json',
                url : '/auth/permission/authz?roleId='+roleId+"&resourceIds="+submitData
            }).done(function(result) {
                if (result.status === 'ok') {
                    resources = result.data;
                    alert("提交成功")
                } else {
                    alert(result.data)
                }
            }).fail(function(resp, msg, err) {
                alert(msg)
            })
            //alert(submitData)
        })

        var loading = function() {
            $.ajax({
                type : 'get',
                dataType : 'json',
                url : '/auth/resource/proto/index?pageSize=10000',
                async: false
            }).done(function(result) {
                if (result.status === 'ok') {
                    resources = result.data;
                } else {
                    alert(result.data)
                }
            }).fail(function(resp, msg, err) {
                alert(msg)
            })

            var id = utils.getPageQuery().id
            $.ajax({
                type : 'get',
                dataType : 'json',
                url : '/auth/permission/get?roleId='+id,
                async: false
            }).done(function(result) {
                if (result.status === 'ok') {
                    permission = result.data;
                    initTree();
                } else {
                    alert(result.data)
                }
            }).fail(function(resp, msg, err) {
                alert(msg)
            })
        }
        loading();
    })

