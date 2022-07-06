$('#nickname').focusout(function() {
    const nickname = $('#nickname').val();
    if(nickname != '') {
        $.ajax({
            url : "/join/nicknameCheck",
            type : "post",
            data : {nickname:nickname},
            dataType : 'json',
            success : function(result) {
                if(result == 0) {
                    $('#nicknameCheck').text('사용 가능한 닉네임입니다.');
                    $('#nicknameCheck').css("color","green");
                } else if(result == 1) {
                    $('#nicknameCheck').text('공백 혹은 특수 문자는 입력하실 수 없습니다.');
                    $('#nicknameCheck').css("color","red");
                } else if(result == 2) {
                    $('#nicknameCheck').text('닉네임은 2~10자리 이내여야 합니다.');
                    $('#nicknameCheck').css("color","red");
                } else {
                    $('#nicknameCheck').text('사용할 수 없는 닉네임입니다.');
                    $('#nicknameCheck').css("color","red");
                }
            },
            error : function() {
                alert("서버 요청 실패");
            }
        })
    }
})