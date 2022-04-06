package com.doit.study.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Integer comment_Id;
    private Integer board_Id;
    private Integer parentComment_Id;
    private String  comment_Content;
    private String  comment_Writer;
    private Date    reg_Date;
    private Date    update_Date;
}
