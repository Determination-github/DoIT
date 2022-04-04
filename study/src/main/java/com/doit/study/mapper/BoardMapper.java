package com.doit.study.mapper;

//import com.doit.study.Board.domain.Board;
import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.BoardWriteDto;
import com.doit.study.board.dto.SearchBoardDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    @Select(BoardSQL.selectAll)
    public List<BoardDto> selectAll();

    @Select(BoardSQL.count)
    public int count();

    @Delete(BoardSQL.deleteAll)
    public int deleteAll();

//    @Delete(BoardSQL.delete)
//    public int delete(Integer board_Id, String board_Writer);

    @Delete(BoardSQL.delete)
    public int delete(BoardDto boardDto);

    //    @Delete(BoardSQL.delete)
    public int delete(String board_Title);

    @Insert(BoardSQL.insert)
    public void insert(BoardDto boardDto);

    @Insert(BoardSQL.insertBoard)
    Integer insertStudyBoard(@Param("board") Board board);

    @Select(BoardSQL.getBoard)
    Optional<Board> findById(@Param("study_id") String study_id);

    @Select(BoardSQL.select)
    public BoardDto selectOne(Integer board_Id);

    @Select(BoardSQL.selectPage)
    public List<BoardDto> selectPage(Pagination pagination);

    @Update(BoardSQL.update)
    public int update(BoardDto boardDto);

    @Update(BoardSQL.increaseViewCount)
    public int increaseViewCount(Integer board_Id);

    @Select(BoardSQL.searchSelectPage)
    public List<SearchBoardDto> searchSelectPage(SearchBoardDto searchBoardDto);

    @Select(BoardSQL.searchResultCount)
    public int searchResultCount(SearchBoardDto searchBoardDto);

}