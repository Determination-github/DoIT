package com.doit.study.board.service;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;

import java.util.List;

public interface GetBoardListService {

    //모집 중인 전체 스터디 글 가져오기
    List<BoardDto> getBoardList(Integer id, Pagination pagination);
}
