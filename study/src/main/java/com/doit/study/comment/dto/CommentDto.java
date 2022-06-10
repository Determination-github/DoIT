package com.doit.study.comment.dto;

import com.doit.study.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    Integer comment_id, study_id, writer_id;
    Integer group_id, group_indent;
    String comment, nickname;
    Date reg_date;

    //프로필 사진 경로
    String path;

    public Comment toEntity(CommentDto commentDto) {
        return Comment.builder()
                .comment_id(comment_id)
                .study_id(study_id)
                .writer_id(writer_id)
                .group_id(group_id)
                .group_indent(group_indent)
                .comment(comment)
                .build();
    }

    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setComment_id(comment.getComment_id());
        commentDto.setStudy_id(comment.getStudy_id());
        commentDto.setWriter_id(comment.getWriter_id());
        commentDto.setGroup_id(comment.getGroup_id());
        commentDto.setGroup_indent(comment.getGroup_indent());
        commentDto.setComment(comment.getComment());
        commentDto.setReg_date(comment.getReg_date());

        return commentDto;
    }
}
