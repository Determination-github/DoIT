const navbarMenu = document.querySelector('.dropdown_menu_wrap');
const navbarToggleBtn = document.querySelector('#toggle_btn');
navbarToggleBtn.addEventListener('click', () => {
  navbarMenu.classList.toggle('open');
});

const navbarSubMenu1 = document.querySelector('.dropdown_menu2_menu');
const navbarSubMenuToggle1 = document.querySelector('#dropdown_menu2_mypage');
navbarSubMenuToggle1.addEventListener('click',() => {
  navbarSubMenu1.classList.toggle('open');
});

const navbarSubMenu2 = document.querySelector('.dropdown_menu3_menu');
const navbarSubMenuToggle2 = document.querySelector('#dropdown_menu3_study');
navbarSubMenuToggle2.addEventListener('click',() => {
  navbarSubMenu2.classList.toggle('open');
});

function dropdownBtn(){
  $(document).ready(function(){
    $('.dropdown-submenu a.test').on("click", function(e){
      $(this).next('ul').toggle();
      e.stopPropagation();
      e.preventDefault();
    });
  });
}
dropdownBtn()

function heart(){
  var i = 0;
    $('i').on('click', function(){
        if(i == 0) {
            $(this).attr('class', 'bi-heart-fill');
            i++;
        } else if(i == 1) {
            $(this).attr('class', 'bi-heart');
            i--;
        }
    });
}
heart()