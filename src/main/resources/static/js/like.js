//좋아요 버튼
$(".btn_like").click(function(){

    const board_id = $(this).val();

    if(session_nickname != null) {
      const data = {
            id : session_id,
            study_id : $('#boardId'+board_id).val()
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

    const board_id = $(this).val();

    if(session_nickname != null) {
      const data = {
            id : session_id,
            study_id : $('#boardId'+board_id).val()
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