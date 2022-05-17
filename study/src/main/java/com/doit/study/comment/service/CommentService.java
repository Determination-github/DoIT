package com.doit.study.comment.service;

import com.doit.study.comment.domain.Comment;
import com.doit.study.comment.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    int getCount(Integer study_id);

//    int write(CommentDto commentDto);

    void insertComment(CommentDto commentDto);

    List<CommentDto> getComment(int study_id);

    void updateComment(CommentDto commentDto);

    void deleteComment(Integer comment_id);
}
