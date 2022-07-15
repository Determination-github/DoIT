//임시비밀번호 발급
$("#sendTempPwd").click(function(){
    const data = {
        email : $('#findPassword').val()
    }
    const check = confirm("임시비밀번호를 발급하시겠습니까?")
    if (check === true) {
        $.ajax({
            type:"POST",
            url:"/login/findPassword/",
            dataType : 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            cache : false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        }).done(function(response) {
            alert(response.result);
            window.location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
});