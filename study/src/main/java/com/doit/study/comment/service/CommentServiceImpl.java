package com.doit.study.comment.service;

import com.doit.study.comment.dto.CommentDto;
import com.doit.study.mapper.BoardMapper;
import com.doit.study.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final BoardMapper   boardMapper;
    private final CommentMapper commentMapper;

    @Override
    public int getCount(Integer board_Id) throws Exception {
        return commentMapper.count(board_Id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer comment_Id, Integer board_Id, String comment_Writer) throws Exception {
        int rowCount = boardMapper.updateCommentCount(board_Id, -1);
        log.info("updateCommentCount - rowCount = " + rowCount);
        rowCount = commentMapper.delete(comment_Id, comment_Writer);
        log.info("rowCount = " + rowCount);
        return rowCount;
    }

    @Override
    public int removeAll(Integer board_Id) throws Exception {
        return commentMapper.deleteAll(board_Id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int write(CommentDto commentDto) throws Exception {
        boardMapper.updateCommentCount(commentDto.getBoard_Id(), 1);
        return commentMapper.insert(commentDto);
    }

    @Override
    public List<CommentDto> getList(Integer board_Id) throws Exception {
        return commentMapper.selectAll(board_Id);
    }

    @Override
    public CommentDto read(Integer comment_Id) throws Exception {
        return commentMapper.select(comment_Id);
    }

    @Override
    public int modify(CommentDto commentDto) throws Exception {
        return commentMapper.update(commentDto);
    }

}
