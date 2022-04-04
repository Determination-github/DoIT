package com.doit.study.board.service;

import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.domain.SearchCondition;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.BoardWriteDto;
import com.doit.study.board.dto.SearchBoardDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BoardService {

    List<BoardDto> getList() throws Exception;
    int getCount() throws Exception;
    List<BoardDto> getPage(Pagination pagination) throws Exception;
    BoardDto read(Integer board_Id) throws Exception;
    int modify(BoardDto boardDto) throws Exception;
    void write(BoardDto boardDto) throws Exception;
    int remove(BoardDto boardDto) throws Exception;
    int remove(String board_Writer) throws Exception;
//    int searchResultCount() throws Exception;
//    List<BoardDto> searchSelectPage(Pagination pagination) throws Exception;
//    int searchResultCount(SearchCondition sc) throws Exception;
//    List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception;
    int searchResultCount(SearchBoardDto searchBoardDto) throws Exception;
    List<SearchBoardDto> searchSelectPage(SearchBoardDto searchBoardDto) throws Exception;

    String insertStudyBoard(BoardWriteDto boardWriteDto);

    BoardWriteDto findStudyById(String study_id, BoardWriteDto boardWriteDto);

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