package com.doit.study.comment.dto;

import com.doit.study.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    int comment_id, study_id, writer_id;
    int group_id, group_indent;
    String comment, nickname;
    Date reg_date;

    public Comment toEntity(CommentDto commentDto) {
        return Comment.builder()
                .study_id(study_id)
                .writer_id(writer_id)
                .comment(comment)
                .build();
    }
}
