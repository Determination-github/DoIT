package com.doit.study.board.service;

import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.BoardWriteDto;
import com.doit.study.board.dto.SearchBoardDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BoardService {

    List<BoardDto> getList();
//    List<BoardDto> getPage(Pagination pagination) throws Exception;
//    BoardDto read(Integer board_Id) throws Exception;
    int modify(BoardDto boardDto);
    void write(BoardDto boardDto);
//    int remove(BoardDto boardDto);
//    int remove(String board_Writer);

    int searchResultCount(SearchBoardDto searchBoardDto);

    List<SearchBoardDto> searchSelectPage(SearchBoardDto searchBoardDto);

    Integer getCount();

    List<BoardWriteDto> getStudyBoardList(Pagination pagination);

    String insertStudyBoard(BoardWriteDto boardWriteDto);

    BoardWriteDto findResultById(String study_id, BoardWriteDto boardWriteDto);

    BoardWriteDto findStudyById(String study_id);

//    void increasViewCount(String id);

    int updateCommentCount(Integer board_Id, int count);




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
