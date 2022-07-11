package com.doit.study.board.service;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.SearchDto;

import java.util.List;

public interface SearchBoardService {

    //검색 내용에 따른 게시글 개수
    Integer getCountBySearching(SearchDto searchDto);

    //검색한 스터디 글 가져오기
    List<BoardDto> getSearchStudyBoardList(Integer id, SearchDto searchDto, Pagination pagination);
}
