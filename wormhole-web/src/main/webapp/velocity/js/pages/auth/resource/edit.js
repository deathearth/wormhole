define(['jquery', 'tabhelper', 'utils/utils'], function ($, tabHelper, utils) {

    $('form').on('submit', function(event) {
        var $form = $(this)
        var data = utils.serializeJson($form)
        $.ajax({
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            url: '/auth/resource/proto/put',
            data : JSON.stringify(data),
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

})