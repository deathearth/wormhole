define(['jquery', 'lodash', 'tabhelper', 'moment', 'utils/utils', 'utils/pagination', 'ztree'], 
function($, _, tabHelper, moment, utils, Pagination) {
    var $tabletree = $('[role=tabletree]')
    var $search = $('[role=search]')
    // 表格树
    var render = _.template($('#tableTreeTmpl').html())

    var propagateExpand = function ($expand) {
		if (!$expand || $expand.length == 0) { return }
		var id = $expand.data('id')
		$expand.addClass('expand')
		var childRows = $tabletree.find('[pid=' + id + ']')
		childRows.show()
		for (var i = 0; i < childRows.length; i++) {
			var $trigger = childRows.eq(i).find('.trigger')
			if ($trigger.is('.expand')) {
				propagateExpand($trigger)
			} else {
				continue
			}
		}

		nodesMap[id].expand = true
	}
	var propagateContract = function ($expand) {
		if (!$expand || $expand.length == 0) { return }
		var id = $expand.data('id')
		var childRows = $tabletree.find('[pid=' + id + ']')
		childRows.hide()
		for (var i = 0; i < childRows.length; i++) {
			var $trigger = childRows.eq(i).find('.trigger')
			if ($trigger.is('.expand')) {
				propagateContract($trigger)
			} else {
				continue
			}
		}
	}
	var doContract = function ($expand) {
		var id = $expand.data('id')
		$expand.removeClass('expand')
		propagateContract($expand)

		nodesMap[id].expand = false
	}
    var renderTableTree = function (nodes, pid, level, parentExpand) {
        var markup = render({
            nodes: nodes,
            level: level,
            pid: pid,
            call: renderTableTree,
            parentExpand: parentExpand
        })
        return markup
    }
    var nodesMap = {}
    var transformNodes = function (resData) {
        var lastMap = nodesMap
        nodesMap = {}

        var roots = [], children = []
        _.forEach(resData, function (r) {
            var lastHasNode = lastMap[r.id]
            var node = {
                id: r.id,
                pid: r.pid,
                name: r.name,
                category: r.category,
                seq: r.seq,
                children: [],
                expand: lastHasNode && lastHasNode.expand
            }
            if (node.pid === 0) {
                roots.push(node)
            } else {
                children.push(node)
            }
            nodesMap[node.id] = node
        })
        _.forEach(children, function (node) {
            var parent = nodesMap[node.pid]
            if (parent) {
                parent.children.push(node)
            } else {
                root.push(node) // 找不到父节点的直接视为根节点，以应对搜索时的情况
                //console.log('can\'t find parentNode which id is: ' + node.pid + ', please check data')
            }
        })
        return roots
    }

	$tabletree.on('click', '.trigger', function () {
		var id = $(this).data('id')
		var isExpand = $(this).is('.expand')
		if (isExpand) {
			doContract($(this))
		} else {
			propagateExpand($(this))
		}
	})

    var initTableTree = function (data) {
        var roots = transformNodes(data)
        var markup = renderTableTree(roots, 0, 0, false)
        $tabletree.html(markup)
    }
    var loading = function () {
    		var query = utils.serializeJson($search)
        $.ajax({
            type: 'get',
            dataType: 'json',
            url: '/auth/resource/proto/index',
          	data: query,
            async: false
        }).done(function (result) {
            if (result.status === 'ok') {
                initTableTree(result.data);
            } else {
                alert(result.data)
            }
        }).fail(function (resp, msg, err) {
            alert(msg)
        })
    }
    
    loading();
    
    $('[role=add]').on('click', function () {
        tabHelper.openChildTab('/auth/resource/add')
    })
    //跳转到编辑页面
    $tabletree.on('click', '[role=edit]', function () {
        var id = $(this).data('id')
        tabHelper.openChildTab('/auth/resource/edit?id=' + id)
    })
    //删除
    $tabletree.on('click', '[role=delete]', function () {
        var ok = confirm('确认删除吗?')
        if (ok) {
            var id = $(this).data('id')
            $.ajax({
                type: 'get',
                dataType: 'json',
                contentType: 'application/json',
                url: '/auth/resource/delete/node/'+id,
               
            }).done(function (result) {
                if (result.status === 'ok') {
                    alert("删除成功")
                    loading();
                } else {
                    alert(result.data)
                }
            }).fail(function (resp, msg, err) {
                alert(msg)
            })
        }
    })
    //新增子节点
    $tabletree.on('click', '[role=add_sub]', function () {
        var id = $(this).data('id')
        tabHelper.openChildTab('/auth/resource/add_sub?id=' + id)
    })

})

