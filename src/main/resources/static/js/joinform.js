$('#name').focusout(function() {
    const name = $('#name').val();
    if(name != '') {
        $.ajax({
            url : "/join/nameCheck",
            type : "post",
            data : {name:name},
            dataType : 'json',
            cache : false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success : function(result) {
                if(result == 1) {
                    $('#nameCheck').text('한글 이름 형태로 입력해주세요.');
                    $('#nameCheck').css("color","red");
                    $('#button').attr("disabled", true);
                } else {
                    $('#nameCheck').text('사용 가능한 이름입니다.');
                    $('#nameCheck').css("color","green");
                }
            },
            /*error : function() {
                alert("서버 요청 실패");
                    $('#button').attr("disabled", true);
            }*/
        })
    }
})

$('#nickname').focusout(function() {
    const nickname = $('#nickname').val();
    if(nickname != '') {
        $.ajax({
            url : "/join/nicknameCheck",
            type : "post",
            data : {nickname:nickname},
            dataType : 'json',
            cache : false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success : function(result) {
                if(result == 0) {
                    $('#nicknameCheck').text('사용 가능한 닉네임입니다.');
                    $('#nicknameCheck').css("color","green");
                } else if(result == 1) {
                    $('#nicknameCheck').text('공백 혹은 특수 문자는 입력하실 수 없습니다.');
                    $('#nicknameCheck').css("color","red");
                    $('#button').attr("disabled", true);
                } else if(result == 2) {
                    $('#nicknameCheck').text('닉네임은 2~10자리 이내여야 합니다.');
                    $('#nicknameCheck').css("color","red");
                    $('#button').attr("disabled", true);
                } else {
                    $('#nicknameCheck').text('사용할 수 없는 닉네임입니다.');
                    $('#nicknameCheck').css("color","red");
                    $('#button').attr("disabled", true);
                }
            },
            error : function() {
                alert("서버 요청 실패");
                    $('#button').attr("disabled", true);
            }
        })
    }
})


$('#email').focusout(function() {
    const email = $('#email').val();
        if(email != '') {
        $.ajax({
            url : "/join/emailCheck",
            type : "post",
            data : {email:email},
            dataType : 'json',
            cache : false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success : function(result) {
                if(result == 0) {
                    $('#emailCheck').text('사용 가능한 이메일입니다.');
                    $('#emailCheck').css("color","green");
                } else if(result == 2) {
                    $('#emailCheck').text('이메일 형식에 맞지 않습니다.');
                    $('#emailCheck').css("color","red");
                    $('#button').attr("disabled", true);
                } else {
                    $('#emailCheck').text('이미 사용 중인 이메일입니다.');
                    $('#emailCheck').css("color","red");
                    $('#button').attr("disabled", true);
                }
            },
            error : function() {
                alert("서버 요청 실패");
                    $('#button').attr("disabled", true);
            }
        })
    }
})

$(function(){
//비밀번호 확인
    $('#password_check').blur(function(){
       if($('#password').val() != $('#password_check').val()){
            if($('#password_check').val()!=''){
                $('#same').text('비밀번호가 일치하지 않습니다.');
                $('#same').css("color","red");
                $('#button').attr("disabled", true);
            }
       }
       else if($('#password').val() == $('#password_check').val())  {
           if($('#password_check').val()!=''){
                $('#same').text('비밀번호가 일치합니다.');
                $('#same').css("color","green");
           }
       }
    })
});


//이메일 인증
$("#emailChk").click(function(){
    let code = "";
    const email = $("#email").val();
    if(!email.length) {
        alert("이메일을 입력해주세요.");
    } else {
        $.ajax({
            type:"POST",
            url:"/join/mailCheck",
            data : {email:email},
            dataType : 'json',
            cache : false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success:function(result){
                if(result == "error"){
                    alert("이메일 주소가 올바르지 않습니다. 유효한 이메일 주소를 입력해주세요.");
                    $("#email").attr("autofocus",true);
                    $(".successEmailChk").text("유효한 이메일 주소를 입력해주세요.");
                    $(".successEmailChk").css("color","red");
                    $('#button').attr("disabled", true);
                }else{
                    alert("인증번호 발송이 완료되었습니다.\n 이메일에서 인증번호 확인을 해주십시오.");
                    $("#sm_email").attr("disabled",false);
                    $(".successEmailChk").text("인증번호를 입력한 뒤 이메일 인증을 눌러주십시오.");
                    $(".successEmailChk").css("color","green");
                    code = result;

                    //이메일 인증번호 대조
                    $("#emailChk2").click(function(){
                        if($("#sm_email").val() != '') {
                            if($("#sm_email").val() == code){
                                $(".successEmailChk").text("인증번호가 일치합니다.");
                                $(".successEmailChk").css("color","green");
                                $("#emailDoubleChk").val("true");
                                $("#sm_email").attr("disabled",true);
                                $('#button').attr("disabled", false);
                            }else{
                                $(".successEmailChk").text("인증번호가 일치하지 않습니다. 확인해주시기 바랍니다.");
                                $(".successEmailChk").css("color","red");
                                $("#emailDoubleChk").val("false");
                                $("#sm_email").attr("autofocus",true);
                                $('#button').attr("disabled", true);
                            }
                        }
                        else if($("#sm_email").val().length == 0) {
                                $(".successEmailChk").text("인증번호를 입력해주세요.");
                                $(".successEmailChk").css("color","red");
                                $('#button').attr("disabled", true);
                        }
                    });
                }
            }
        });
    }
});

