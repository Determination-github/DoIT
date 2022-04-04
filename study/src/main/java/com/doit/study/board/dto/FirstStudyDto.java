package com.doit.study.board.dto;

import lombok.Data;

@Data
public class FirstStudyDto {

    private String writerId, nickName;
    private String interest1, interest2, interest3;
    private String startDate, endDate;
    private String location, subTitle;
    private int onOffLine;

}
