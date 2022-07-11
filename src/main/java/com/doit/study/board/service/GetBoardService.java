package com.doit.study.board.service;

import com.doit.study.board.dto.BoardDto;

public interface GetBoardService {

    //전체 스터디 글 개수
    Integer getCount();

    //작성한 글 개수 BY 회원아이디
    Integer getCountById(Integer id);

    //게시글 정보 가져오기
    BoardDto findResultById(Integer study_id, BoardDto boardWriteDto);

    //게시글 정보 가져오기(BoardDto 값이 없을 때)
    BoardDto findStudyById(Integer study_id);
}
