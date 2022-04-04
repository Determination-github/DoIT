function heart(){
  let i = 0;
    $('.main_heart_btn').on('click', function(){
        if(i == 0) {
            $(this).attr('class', 'bi-heart-fill');
            i++;
        } else if(i == 1) {
            $(this).attr('class', 'bi-heart');
            i--;
        }
    });
}
heart();