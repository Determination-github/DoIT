package com.doit.study.comment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Getter
@NoArgsConstructor
public class Comment {
    Integer comment_id, study_id, writer_id;
    Integer group_id, group_indent;
    String comment;
    Date reg_date;

    @Builder
    public Comment(Integer study_id, Integer writer_id, Integer group_id,
                   Integer group_indent, String comment) {
        this.study_id = study_id;
        this.writer_id = writer_id;
        this.group_id = group_id;
        this.group_indent = group_indent;
        this.comment = comment;
    }
}
