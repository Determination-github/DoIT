package com.doit.study.mapper;

import com.doit.study.comment.domain.Comment;
import com.doit.study.comment.dto.CommentDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select(CommentSQL.count)
    int count(String board_Id);

    @Delete(CommentSQL.deleteAll)
    int deleteAll(String board_Id);

    @Delete(CommentSQL.delete)
    int delete(@Param("comment_Id") int comment_Id, @Param("comment_Writer") String comment_Writer);

    @Select(CommentSQL.selectAll)
    List<CommentDto> selectAll(String board_Id);

    @Select(CommentSQL.select)
    CommentDto select(int comment_Id);

    @Update(CommentSQL.update)
    int update(CommentDto commentDto);

    @Insert(CommentSQL.insert)
    void insert(@Param("comment") Comment comment);

    @Select(CommentSQL.getComment)
    List<Comment> getComment(int study_id);

    @Select(CommentSQL.getNickname)
    String getNicknameById(@Param("comment") Comment comment);
}
