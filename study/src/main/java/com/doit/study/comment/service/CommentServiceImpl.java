package com.doit.study.comment.service;

import com.doit.study.comment.domain.Comment;
import com.doit.study.comment.dto.CommentDto;
import com.doit.study.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentMapper commentMapper;

    @Override
    public Integer getCount(Integer study_id) {
        return commentMapper.count(study_id);
    }

    @Override
    public void insertComment(CommentDto commentDto) {
        Comment comment = getComment(commentDto);
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
        Comment comment = getComment(commentDto);
        commentMapper.modify(comment);
    }

    @Override
    public void deleteComment(Integer comment_id) {
        commentMapper.delete(comment_id);
    }

    //dto to entity
    private Comment getComment(CommentDto commentDto) {
        return commentDto.toEntity(commentDto);
    }

}
