package com.doit.study.comment.service;

import com.doit.study.comment.domain.Comment;
import com.doit.study.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    int getCount(String board_Id);

    int remove(Integer comment_Id, String board_Id, String comment_Writer) throws Exception;

    int removeAll(String board_Id);

//    int write(CommentDto commentDto);

    List<CommentDto> getList(String board_Id);

    CommentDto read(Integer comment_Id);

    int modify(CommentDto commentDto);

    void insertComment(CommentDto commentDto);

    List<CommentDto> getComment(int study_id);

    void updateComment(CommentDto commentDto);
}
