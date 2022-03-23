package com.doit.study.board.service;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;

import java.util.List;

public interface BoardService {

    List<BoardDto> getList() throws Exception;
    int getCount() throws Exception;
    List<BoardDto> getPage(Pagination pagination) throws Exception;
    BoardDto read(Integer board_Id) throws Exception;
    int modify(BoardDto boardDto) throws Exception;
    int write(BoardDto boardDto) throws Exception;
    int remove(Integer board_Id, String board_Writer) throws Exception;
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
