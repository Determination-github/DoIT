package com.doit.study.mapper;

//import com.doit.study.Board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Select(BoardSQL.selectAll)
    List<BoardDto> selectAll();

    @Select(BoardSQL.count)
    int count();

    @Delete(BoardSQL.deleteAll)
    int deleteAll();

    @Delete(BoardSQL.delete)
    int delete(Integer board_Id, String board_Writer);

    @Insert(BoardSQL.insert)
    int insert(BoardDto boardDto);

    @Select(BoardSQL.select)
    BoardDto selectOne(Integer board_Id);

    @Select(BoardSQL.selectPage)
    List<BoardDto> selectPage(Pagination pagination);

    @Update(BoardSQL.update)
    int update(BoardDto boardDto);

    @Update(BoardSQL.increaseViewCount)
    int increaseViewCount(Integer board_Id);
}

