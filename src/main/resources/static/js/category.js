/*  eslint-disable no-unused-vars */

const category_num = new Array(1, 2, 3);
const category_name = new Array('개발', '자격증', '프로젝트');

const category2_num = new Array();
const category2_name = new Array();

const category3_num = new Array();
const category3_name = new Array();

category2_num[1] = new Array(4, 5);
category2_name[1] = new Array('Back-End', 'Front-End');

category2_num[2] = new Array(6, 7);
category2_name[2] = new Array('기사자격증', '산업기사');

category2_num[3] = new Array(8, 9, 10);
category2_name[3] = new Array('백엔드', '프론트엔드', '풀스택');

category3_num[4] = new Array(1, 2, 3);
category3_name[4] = new Array('자바', '스프링', '파이썬');

category3_num[5] = new Array(4, 5);
category3_name[5] = new Array('리액트', '뷰');

category3_num[6] = new Array(6, 7, 8);
category3_name[6] = new Array('정보처리기사', '정보보안기사', '방송통신기사');

category3_num[7] = new Array(9, 10 ,11);
category3_name[7] = new Array('정보처리산업기사', '정보보안산업기사', '방송통신산업기사');

category3_num[8] = new Array(12, 13, 14);
category3_name[8] = new Array('스프링 프로젝트', '자바 프로젝트', '안드로이드 프로젝트');

category3_num[9] = new Array(15, 16);
category3_name[9] = new Array('리액트 프로젝트', '뷰 프로젝트');

category3_num[10] = new Array(17, 18);
category3_name[10] = new Array('웹 포털 프로젝트', '스마트폰 앱 기반 프로젝트');


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

function category2_change(key, sel) {
  if(key == '') return;
  let name = category3_name[key];
  let val = category3_num[key];
  let i = 0;

  for(i = sel.length -1; i >= 0; i--)
  sel.options[i] = null;
  sel.options[0] = new Option('선택하세요', '', '', 'true');
  for(i = 0; i < name.length; i++) {
    sel.options[i + 1] = new Option(name[i], name[i]);
  }
}