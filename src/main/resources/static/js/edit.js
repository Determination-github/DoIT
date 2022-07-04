var state1 = true;
var state2;

$('#nickname').focusout(function() {
    let nickname = $('#nickname').val();
    let originalNickname = $('#originalNickname').val();
    nickname.trim();
    if(nickname != originalNickname) {
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
                        state1 = true;
                        checkState();
                    } else if(result == 3) {
                        $('#nicknameCheck').text('공백 혹은 특수 문자는 입력하실 수 없습니다.');
                        $('#nicknameCheck').css("color","red");
                        state1 = false;
                        checkState();
                    } else if(result == 2) {
                        $('#nicknameCheck').text('닉네임은 2~10자리 이내여야 합니다.');
                        $('#nicknameCheck').css("color","red");
                        state1 = false;
                        checkState();
                    } else {
                        $('#nicknameCheck').text('사용할 수 없는 닉네임입니다.');
                        $('#nicknameCheck').css("color","red");
                        state1 = false;
                        checkState();
                    }
                },
                error : function() {
                    alert("서버 요청 실패");
                }
            })
        }
   }
})

$('#password').focusout(function() {
    let password = $('#password').val();
    password.trim();
    if(password != '') {
        $.ajax({
            url : "/join/passwordCheck",
            type : "post",
            data : {password:password},
            dataType : 'json',
            success : function(result) {
                if(result == 0) {
                    $('#passwordCheck').text('사용 가능한 비밀번호입니다.');
                    $('#passwordCheck').css("color","green");
                    checkState();
                } else if(result == 1) {
                    $('#passwordCheck').text('비밀번호는 8~16자 영문 소문자와 숫자, 특수문자를 사용하세요.');
                    $('#passwordCheck').css("color","red");
                    state2 = false
                    checkState();
                }
            },
            error : function() {
                alert("서버 요청 실패");
                    $('#modify-btn').attr("disabled", true);
            }
        })
    }
})

$(function(){
//비밀번호 확인
    $('#password-two').blur(function(){
       if($('#password').val() != $('#password-two').val()){
            if($('#password-two').val()!=''){
                $('#doubleCheck').text('비밀번호가 일치하지 않습니다.');
                $('#doubleCheck').css("color","red");
                state2 = false;
                checkState();
            }
       }
       else if($('#password').val() == $('#password-two').val())  {
           if($('#password-two').val()!=''){
                $('#doubleCheck').text('비밀번호가 일치합니다.');
                $('#doubleCheck').css("color","green");
                state2 = true;
                checkState();
           }
       }
    })
});

var checkState = function(){
    if(state1 == true && state2 == null) {
      $('#modify-btn').attr("disabled", false);
    } else if(state1 == true && state2 == true) {
      $('#modify-btn').attr("disabled", false);
    } else {
      $('#modify-btn').attr("disabled", true);
    }
}

//프로필 수정
$("#modify-btn").click(function(){
    let data = {
        id : $('#id').val(),
        password : $('#password-two').val(),
        nickname : $('#nickname').val()
    }

    const check = confirm("수정하시겠습니까?")
    if (check === true) {
        $.ajax({
            type:"PUT",
            url:"/profile/update/" + data.id,
            dataType : 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            cache : false,
        }).done(function() {
            alert("회원정보가 수정되었습니다.");
            location.href = "/profile/"+data.id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
});

//회원 정보 삭제
$("#floatingDelete").click(function(){
    let data = {
        id : $('#id').val(),
        text : $('#deleteText').val()
    }
    if(data.text === "삭제") {
        const check = confirm("회원 정보를 삭제하시겠습니까?")
        if (check === true) {
            $.ajax({
                type:"DELETE",
                url:"/profile/delete/" + data.id,
                dataType : 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                cache : false,
            }).done(function() {
                alert("회원정보가 성공적으로 삭제되었습니다.");
                location.href = "/";
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    } else {
        alert("잘못 입력했습니다.");
    }
});