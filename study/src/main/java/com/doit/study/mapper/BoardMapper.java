package com.doit.study.mapper;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Select(BoardSQL.selectAll)
    public List<BoardDto> selectAll();

    @Select(BoardSQL.count)
    public int count();

    @Delete(BoardSQL.deleteAll)
    public int deleteAll();

    @Delete(BoardSQL.delete)
    public int delete(BoardDto boardDto);

    @Insert(BoardSQL.insert)
    public void insert(BoardDto boardDto);

    @Select(BoardSQL.select)
    public BoardDto selectOne(Integer board_Id);

    @Select(BoardSQL.selectPage)
    public List<BoardDto> selectPage(Pagination pagination);

    @Update(BoardSQL.update)
    public int update(BoardDto boardDto);

    @Update(BoardSQL.increaseViewCount)
    public int increaseViewCount(Integer board_Id);

    @Select(BoardSQL.searchSelectPage)
    public List<BoardDto> searchSelectPage(BoardDto boardDto);

    @Select(BoardSQL.searchResultCount)
    public int searchResultCount(BoardDto boardDto);

    @Update(BoardSQL.updateCommentCount)
    public int updateCommentCount(@Param("board_Id") Integer board_Id, @Param("count") Integer count);

}

