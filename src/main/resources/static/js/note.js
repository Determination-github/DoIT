$(function() {
//    function removeNote() {
//        $(".remove-note").off('click').on('click', function(event) {
//          event.stopPropagation();
//          $(this).parents('.single-note-item').remove();
//        });
//    }

    var $btns = $('.note-link').click(function() {
        if (this.id == 'all-category') {
          var $el = $('.' + this.id).fadeIn();
          $('#note-full-container > div').not($el).hide();
        } if (this.id == 'important') {
          var $el = $('.' + this.id).fadeIn();
          $('#note-full-container > div').not($el).hide();
        } else {
          var $el = $('.' + this.id).fadeIn();
          $('#note-full-container > div').not($el).hide();
        }
        $btns.removeClass('active');
        $(this).addClass('active');
    })
});


function delete_note(object) {
    $(".remove-note").off('click').on('click', function(event) {
      event.stopPropagation();
      let data = {
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
            }).done(function() {
                alert("쪽지가 삭제되었습니다.");
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    });
}