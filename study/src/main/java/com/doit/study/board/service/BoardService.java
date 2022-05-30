package com.doit.study.board.service;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.SearchDto;

import java.util.List;

public interface BoardService {

    //전체 스터디 글 개수
    Integer getCount();

    //전체 스터디 글 가져오기
    List<BoardDto> getStudyBoardList(Pagination pagination);

    //글 추가하기
    Integer insertStudyBoard(BoardDto boardWriteDto);

    //게시글 정보 가져오기
    BoardDto findResultById(int study_id, BoardDto boardWriteDto);

    //게시글 정보 가져오기(BoardDto 값이 없을 때)
    BoardDto findStudyById(int study_id);

    //게시글 수정하기
    BoardDto updateBoard(BoardDto boardDto);

    //게시글 삭제하기
    Integer deleteBoard(int study_id);

    Integer getCountBySearching(SearchDto searchDto);


    //Integer getCountMyStudy(String id);





}



























//    int remove(Integer bno, String writer) throws Exception;
//    int write(BoardDto boardDto) throws Exception;
//    List<BoardDto> getList() throws Exception;
//    BoardDto read(Integer bno) throws Exception;
//    List<BoardDto> getPage(Map map) throws Exception;
//    int modify(BoardDto boardDto) throws Exception;
//
//    int getSearchResultCnt(SearchCondition sc) throws Exception;
//    List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception;
//}
