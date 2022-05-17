package com.doit.study.mapper;

import com.doit.study.comment.domain.Comment;
import com.doit.study.comment.dto.CommentDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select(CommentSQL.count)
    int count(String board_Id);

    @Insert(CommentSQL.insert)
    void insert(@Param("comment") Comment comment);

    @Select(CommentSQL.getComment)
    List<Comment> getComment(int study_id);

    @Select(CommentSQL.getNickname)
    String getNicknameById(@Param("comment") Comment comment);

    @Update(CommentSQL.modifyReply)
    void modify(@Param("comment") Comment comment);

    @Delete(CommentSQL.delete)
    void delete(@Param("comment_id") Integer comment_id);
}
