package com.doit.study.comment.service;

import com.doit.study.comment.domain.Comment;
import com.doit.study.comment.dto.CommentDto;
import com.doit.study.mapper.BoardMapper;
import com.doit.study.mapper.CommentMapper;
import com.doit.study.mapper.CommentSQL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final BoardMapper   boardMapper;
    private final CommentMapper commentMapper;

    @Override
    public int getCount(String board_Id) {
        return commentMapper.count(board_Id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer comment_Id, String board_Id, String comment_Writer) {
        int rowCount = boardMapper.updateCommentCount(board_Id, -1);
        log.info("updateCommentCount - rowCount = " + rowCount);
        rowCount = commentMapper.delete(comment_Id, comment_Writer);
        log.info("rowCount = " + rowCount);
        return rowCount;
    }

    @Override
    public int removeAll(String board_Id) {
        return commentMapper.deleteAll(board_Id);
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int write(CommentDto commentDto) {
//        boardMapper.updateCommentCount(commentDto.getBoard_Id(), 1);
//        return commentMapper.insert(commentDto);
//    }

    @Override
    public List<CommentDto> getList(String board_Id) {
        return commentMapper.selectAll(board_Id);
    }

    @Override
    public CommentDto read(Integer comment_Id) {
        return commentMapper.select(comment_Id);
    }

    @Override
    public int modify(CommentDto commentDto) {
        return commentMapper.update(commentDto);
    }

    @Override
    public void insertComment(CommentDto commentDto) {
        Comment comment = commentDto.toEntity(commentDto);
        commentMapper.insert(comment);
    }

    @Override
    public List<CommentDto> getComment(int study_id) {
        List<Comment> commentList = commentMapper.getComment(study_id);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment : commentList) {
            //닉네임 가져오기
            String nickname = commentMapper.getNicknameById(comment);
            CommentDto commentDto = new CommentDto().toDto(comment);
            commentDto.setNickname(nickname);
            commentDtos.add(commentDto);
        }

        return commentDtos;
    }

    @Override
    public void updateComment(CommentDto commentDto) {
        Comment comment = commentDto.toEntity(commentDto);

        log.info("수정");
        log.info("study_id={}", comment.getStudy_id());
        log.info("comment_id={}", comment.getComment_id());
        log.info("group_id={}", comment.getGroup_id());
        log.info("comment={}", comment.getComment());
        commentMapper.modify(comment);
    }

}
