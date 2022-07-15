$(function() {

    const $btns = $('.note-link').click(function() {
        if (this.id == 'all-category') {
          let $el = $('.' + this.id).fadeIn();
          $('#note-full-container > div').not($el).hide();
        } if (this.id == 'important') {
          let $el = $('.' + this.id).fadeIn();
          $('#note-full-container > div').not($el).hide();
        } else {
          let $el = $('.' + this.id).fadeIn();
          $('#note-full-container > div').not($el).hide();
        }
        $btns.removeClass('active');
        $(this).addClass('active');
    })
});


function delete_note(object) {
    $(".remove-note").off('click').on('click', function(event) {
      event.stopPropagation();
      const data = {
            note_id : $(object).attr('value')
      }
      const check = confirm("쪽지를 삭제하시겠습니까?")
      if (check === true) {
          $(this).parents('.single-note-item').remove();
            $.ajax({
                type:"DELETE",
                url:"/note/" + session_id,
                dataType : 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                cache : false,
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
            }).done(function() {
                alert("쪽지가 삭제되었습니다.");
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    });
}