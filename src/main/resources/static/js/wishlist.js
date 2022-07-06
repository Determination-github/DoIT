//좋아요 버튼 취소
$(".btn_like_delete").click(function(){

    const board_id = $(this).val();

    const data = {
        id : session_id,
        study_id : $('#boardId'+board_id).val()
    }

    const check = confirm("위시리스트에서 제거하시겠습니까?")

    if(check === true) {
        $.ajax({
            type:"DELETE",
            url:"/like/delete/" + data.id,
            dataType : 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            cache : false,
        }).done(function(response) {
            const check = confirm("위시리스트에서 제거되었습니다.")
            window.location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    } else {
        return false;
    }
});
