package com.doit.study.board.service;

import com.doit.study.board.dto.BoardDto;

public interface BoardService {

    //게시글 추가하기
    Integer insertStudyBoard(BoardDto boardWriteDto);

    //게시글 수정하기
    BoardDto updateBoard(BoardDto boardDto);

    //게시글 삭제하기
    Integer deleteBoard(int study_id);

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
