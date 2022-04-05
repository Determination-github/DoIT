package com.doit.study.comment.service;

import com.doit.study.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    public int getCount(Integer board_Id) throws Exception;
    public int remove(Integer comment_Id, Integer board_Id, String comment_Writer) throws Exception;
    public int removeAll(Integer board_Id) throws Exception;
    public int write(CommentDto commentDto) throws Exception;
    public List<CommentDto> getList(Integer board_Id) throws Exception;
    public CommentDto read(Integer comment_Id) throws Exception;
    public int modify(CommentDto commentDto) throws Exception;
}
