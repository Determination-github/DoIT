package com.doit.study.board.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FirstStudyDto {

    private int id;
    private String nickName;
    private String totalLocation;
    private int flag;
    private boolean onOffLine;

    @NotEmpty(message = "날짜를 입력해주세요.")
    private String startDate, endDate;

    @NotEmpty(message = "카테고리를 모두 선택해주세요.")
    private String interest1, interest2, interest3;

    @NotEmpty(message = "지역을 모두 선택해주세요.")
    private String location1, location2;

    @NotEmpty(message = "스터디에 대한 짧은 소개글을 작성해주세요.")
    private String subTitle;

}
