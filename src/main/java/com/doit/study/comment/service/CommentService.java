package com.doit.study.comment.service;

import com.doit.study.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {

    //댓글 수 가져오기
    Integer getCount(Integer study_id);

    //댓글 작성
    void insertComment(CommentDto commentDto) throws Exception;

    //댓글 목록 가져오기
    List<CommentDto> getComment(int study_id);

    //댓글 업데이트
    void updateComment(CommentDto commentDto) throws Exception;

    //댓글 삭제
    void deleteComment(Integer comment_id) throws Exception;
}
