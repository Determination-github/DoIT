//글 수정
$("#edit").click(function(){
    const data = {
        board_id : modify_board_id
    }

    const check = confirm("글을 수정하시겠습니까?")
    if (check === true) {
        location.href = "/board/write/"+data.board_id;
    }
});

//글 삭제
$("#delete").click(function(){
    const id = $(this).val();

    const check = confirm("글을 삭제하시겠습니까?")
    if(check === true) {
        $.ajax({
            type:"DELETE",
            url:"/board/" +id,
            contentType: 'application/json; charset=utf-8',
            cache : false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
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
      const data = {
            id : session_id,
            study_id : $(this).val()
      }
      $.ajax({
          type:"POST",
          url:"/like/" + data.id,
          dataType : 'json',
          contentType: 'application/json; charset=utf-8',
          data: JSON.stringify(data),
          cache : false,
          beforeSend : function(xhr) {
              xhr.setRequestHeader(header, token);
          },
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

    const board_id = $(this).val();

    if(session_nickname != null) {
        let data = {
            id : session_id,
            study_id : $(this).val()
        }

        $.ajax({
          type:"DELETE",
          url:"/like/" + data.id,
          dataType : 'json',
          contentType: 'application/json; charset=utf-8',
          data: JSON.stringify(data),
          cache : false,
          beforeSend : function(xhr) {
              xhr.setRequestHeader(header, token);
          },
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
