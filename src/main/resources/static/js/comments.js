//댓글 작성
$("#add-comment-btn").click(function(){

    let data = {
        writer_id : $("#writerId").val(),
        study_id : $("#boardId").val(),
        comment : $("#comment").val()
    }

    if(session_id === null) {
        alert("로그인이 필요합니다.");
        return false;
    } else if (!data.comment || data.comment.trim() === "") {
        alert("댓글 내용을 입력해주세요.");
        return false;
    } else {
        $.ajax({
            type:"POST",
            url:"/comments/save/" + data.writer_id,
            dataType : 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            cache : false,
            success:function(response){
                if(response.result == 2){
                    alert("댓글은 500자 미만으로 입력해주세요.");
                } else if(response.result == 1){
                    alert("댓글이 등록되었습니다.");
                    window.location.reload();
                    if(id != session_id){
                        stompClient.send("/server/comment/"+comment_write_id, {}, JSON.stringify({
                                                'study_id' : $('#boardId').val(),
                                                'receiver_id' : comment_write_id,
                                                'message' : $('#comment').val()
                        }));
                    }
                } else {
                    alert("댓글 등록 중 오류가 발생했습니다.");
                    console.log(JSON.stringify(error));
                }
            }
        });
    }
});

//대댓글, 수정, 삭제버튼 누를 경우
$(".btn-light").click(function(){
    let id = $(this).val();
    let button = $(this).text();
    let reply = document.getElementById('reply-textarea'+id);
    let re_reply = document.getElementById('re-reply-textarea'+id);

    if(button === '댓글') {
        if(reply.style.display==='none'){
            re_reply.style.display='none';
            reply.style.display='block';
        } else {
            reply.style.display='none';
            re_reply.display='none';
        }
    } else if(button === '수정') {
        if(re_reply.style.display==='none'){
            reply.style.display='none';
            re_reply.style.display='block';
        } else {
            re_reply.style.display='none';
            reply.style.display='none';
        }
    } else {
        //댓글 삭제
        let data = {
            comment_id : id
        }
        const check = confirm("댓글을 삭제하시겠습니까?")
        if (check === true) {
            $.ajax({
                type:"DELETE",
                url:"/comments/delete/reply/" + data.comment_id,
                dataType : 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                cache : false,
            }).done(function() {
                alert("댓글이 삭제되었습니다.");
                window.location.reload();
            }).fail(function (error) {
                alert("댓글 등록 중 오류가 발생했습니다.");
                console.log(JSON.stringify(error));
            });
        }
    }
});


//대댓글 작성
$(".reply-btn").click(function(){
    let id = $(this).val();

    let data = {
        writer_id : $('#writerId'+id).val(),
        study_id : $('#boardId'+id).val(),
        group_id : $('#groupId'+id).val(),
        group_indent : $('#group_indent').val(),
        comment : $('#reply'+id).val()
    }
    if(session_id === null) {
        alert("로그인이 필요합니다.");
        return false;
    } else if (!data.comment || data.comment.trim() === "") {
        alert("댓글 내용을 입력해주세요.");
        return false;
    } else {
        $.ajax({
            type:"POST",
            url:"/comments/save/reply/" + data.writer_id,
            dataType : 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            cache : false,
        }).done(function(response) {
            if(response.result == 2){
                alert("댓글은 500자 미만으로 입력해주세요.");
            } else if(response.result == 1){
                alert("댓글이 등록되었습니다.");
                window.location.reload();
                if(data.writer_id != session_id){
                    stompClient.send("/server/comment/"+id, {}, JSON.stringify({
                                'study_id' : $('#boardId'+id).val(),
                                'receiver_id' : $('#receiverId'+id).val(),
                                'message' : $('#reply'+id).val()
                    }));
                }
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
});

//댓글 수정
$(".reply-modify-btn").click(function(){
    let id = $(this).val();

    let data = {
        writer_id : $('#writerId'+id).val(),
        study_id : $('#boardId'+id).val(),
        group_id : $('#groupId'+id).val(),
        comment_id : id,
        comment : $('#modify'+id).val()
    }

    if (!data.comment || data.comment.trim() === "") {
        alert("댓글 내용을 입력해주세요.");
        return false;
    }
    const check = confirm("수정하시겠습니까?")
    if (check === true) {
        $.ajax({
            type:"PUT",
            url:"/comments/modify/reply/" + data.writer_id,
            dataType : 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            cache : false,
        }).done(function() {
            alert("댓글이 수정되었습니다.");
            window.location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
});

//글 수정
$("#edit").click(function(){
    let data = {
        board_id : modify_board_id
    }

    const check = confirm("글을 수정하시겠습니까?")
    if (check === true) {
        location.href = "/board/write/"+data.board_id;
    }
});

//글 삭제
$("#delete").click(function(){
    let id = $(this).val();

    const check = confirm("글을 삭제하시겠습니까?")
    if(check === true) {
        $.ajax({
            type:"DELETE",
            url:"/board/delete/" +id,
            contentType: 'application/json; charset=utf-8',
            cache : false,
        }).done(function() {
            alert("글이 삭제되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
});

//좋아요 버튼
$(".btn_like").click(function(){

    if(session_nickname != null) {
      let data = {
            id : session_id,
            study_id : $(this).val()
      }
      $.ajax({
          type:"POST",
          url:"/like/save/" + data.id,
          dataType : 'json',
          contentType: 'application/json; charset=utf-8',
          data: JSON.stringify(data),
          cache : false,
      }).done(function(response) {
          const check = confirm("스터디가 위시리스트에 담겼습니다. 위시리스트로 이동하시겠습니까?")
          if (check === true) {
            location.href = "/like/"+response;
          } else {
            window.location.reload();
          }
      }).fail(function (error) {
          alert(JSON.stringify(error));
      });
    } else {
        const check = confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까 ?")
          if (check === true) {
            location.href = "/login";
          } else {
            return false;
          }
    }
});

//좋아요 버튼 취소
$(".btn_like_delete").click(function(){

    let board_id = $(this).val();

    if(session_nickname != null) {
        let data = {
            id : session_id,
            study_id : $(this).val()
        }

        $.ajax({
          type:"DELETE",
          url:"/like/delete/" + data.id,
          dataType : 'json',
          contentType: 'application/json; charset=utf-8',
          data: JSON.stringify(data),
          cache : false,
        }).done(function(response) {
          const check = confirm("위시리스트에서 제거되었습니다. 위시리스트로 이동하시겠습니까?")
          if (check === true) {
            location.href = "/like/"+response;
          } else {
            window.location.reload();
          }
        }).fail(function (error) {
          alert(JSON.stringify(error));
        });
    } else {
        const check = confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까 ?")
          if (check === true) {
            location.href = "/login";
          } else {
            return false;
          }
    }
});
