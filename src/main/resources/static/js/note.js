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