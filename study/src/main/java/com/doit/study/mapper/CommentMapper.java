package com.doit.study.mapper;

import com.doit.study.comment.domain.Comment;
import com.doit.study.comment.dto.CommentDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {

    @Select(CommentSQL.getCount)
    int count(Integer study_id);

    @Insert(CommentSQL.insert)
    void insert(@Param("comment") Comment comment);

    @Select(CommentSQL.getComment)
    List<Comment> getComment(@Param("study_id") Integer study_id);

    @Select(CommentSQL.getNickname)
    String getNicknameById(@Param("comment") Comment comment);

    @Update(CommentSQL.modifyReply)
    void modify(@Param("comment") Comment comment);

    @Delete(CommentSQL.delete)
    void delete(@Param("comment_id") Integer comment_id);
}
