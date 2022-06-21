package com.doit.study.comment.service;

import com.doit.study.comment.domain.Comment;
import com.doit.study.comment.dto.CommentDto;
import com.doit.study.mapper.CommentMapper;
import com.doit.study.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{

    private final CommentMapper commentMapper;
    private final ProfileMapper profileMapper;

    /***
     * 댓글 수 가져오기
     * @param study_id
     * @return Integer
     */
    @Override
    public Integer getCount(Integer study_id) {
        return commentMapper.count(study_id);
    }

    /***
     * 댓글 작성
     * @param commentDto
     */
    @Override
    public void insertComment(CommentDto commentDto) {
        //comment 정보 가져오기
        Comment comment = getComment(commentDto);
        commentMapper.insert(comment);
    }

    /***
     * 댓글 목록 가져오기
     * @param study_id
     * @return List<CommentDto>
     */
    @Override
    public List<CommentDto> getComment(int study_id) {
        //list 객체에 정보 담기
        List<Comment> commentList = commentMapper.getComment(study_id);

        //comment 정보를 담을 list 객체
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment : commentList) {
            //닉네임 가져오기
            String nickname = commentMapper.getNicknameById(comment);
            CommentDto commentDto = new CommentDto().toDto(comment);
            commentDto.setNickname(nickname);

            //프로필 사진 가져오기
            String path = profileMapper.getImagePath(commentDto.getWriter_id());
            if(path != null) {
                commentDto.setPath(path);
            }

            commentDtos.add(commentDto);
        }
        return commentDtos;
    }

    /***
     * 댓글 업데이트
     * @param commentDto
     */
    @Override
    public void updateComment(CommentDto commentDto) {
        Comment comment = getComment(commentDto);

        commentMapper.modify(comment);
    }

    /***
     * 댓글 삭제
     * @param comment_id
     */
    @Override
    public void deleteComment(Integer comment_id) {
        commentMapper.delete(comment_id);
    }

    //dto to entity
    private Comment getComment(CommentDto commentDto) {
        return commentDto.toEntity(commentDto);
    }

}
