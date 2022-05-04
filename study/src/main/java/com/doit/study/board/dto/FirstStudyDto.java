package com.doit.study.board.dto;

import lombok.Data;

@Data
public class FirstStudyDto {

    private int id;
    private String nickName, startDate, endDate;
    private String interest1, interest2, interest3;
    private String location1, location2, totalLocation;
    private String subTitle;
    private int flag;
    private boolean onOffLine;

}
