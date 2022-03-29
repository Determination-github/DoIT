/*  eslint-disable no-unused-vars */

var category_num = new Array(1, 2, 3);
var category_name = new Array('개발', '언어', '자격증');

var category2_num = new Array();
var category2_name = new Array();

category2_num[1] = new Array(4, 5);
category2_name[1] = new Array('Back-End', 'Front-End');

category2_num[2] = new Array(6, 7, 8, 9, 10);
category_name[2] = new Array('C++', 'javascript', '파이썬', 'assembly', 'OpenGL');

category2_num[3] = new Array(11, 12);
category2_name[3] = new Array('정보처리기사', '정보처리산업기사');

function category_change(key, sel) {
  if(key == '') return;
  let name = category2_name[key];
  let val = category2_num[key];
  let i = 0;

  for(i = sel.length -1; i >= 0; i--)
  sel.options[i] = null;
  sel.options[0] = new Option('선택하세요', '', '', 'true');
  for(i = 0; i < name.length; i++) {
    sel.options[i + 1] = new Option(name[i], val[i]);
  }
}