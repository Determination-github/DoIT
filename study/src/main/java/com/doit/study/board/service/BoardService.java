package com.doit.study.board.service;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;

import java.util.List;

public interface BoardService {

//    List<BoardDto> getList();
//    int modify(BoardDto boardWriteDto);
//    void write(BoardDto boardWriteDto);

//    int searchResultCount(SearchBoardDto searchBoardDto);


    Integer getCount();

    Integer getCountMyStudy(String id);

    List<BoardDto> getStudyBoardList(Pagination pagination);

    Integer insertStudyBoard(BoardDto boardWriteDto);

    BoardDto findResultById(String study_id, BoardDto boardWriteDto);

    BoardDto findStudyById(String study_id);

//    void increasViewCount(String id);

//    int updateCommentCount(String board_Id, int count);




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
