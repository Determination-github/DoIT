package com.doit.study.board.dto;

import lombok.Data;

import java.util.List;

@Data
public class SecondStudyDto {

    private int boardId;
    private String title;
    private String content;
    private String regDate;
    private String viewCount, commentCount;
    private String hashtag;

}
