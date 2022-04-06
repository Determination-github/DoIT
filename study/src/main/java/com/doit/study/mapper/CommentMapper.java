package com.doit.study.mapper;

import com.doit.study.comment.dto.CommentDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select(CommentSQL.count)
    int count(Integer board_Id) throws Exception;
    @Delete(CommentSQL.deleteAll)
    int deleteAll(Integer board_Id);
    @Delete(CommentSQL.delete)
    int delete(@Param("comment_Id") Integer comment_Id, @Param("comment_Writer") String comment_Writer) throws Exception;
    @Insert(CommentSQL.insert)
    int insert(CommentDto commentDto) throws Exception;
    @Select(CommentSQL.selectAll)
    List<CommentDto> selectAll(Integer board_Id) throws Exception;
    @Select(CommentSQL.select)
    CommentDto select(Integer comment_Id) throws Exception;
    @Update(CommentSQL.update)
    int update(CommentDto commentDto) throws Exception;
}
