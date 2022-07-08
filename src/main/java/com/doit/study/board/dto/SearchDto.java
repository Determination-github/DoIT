package com.doit.study.board.dto;

import lombok.Data;

@Data
public class SearchDto {

    private String keyword, location1, location2, location;
    private Integer on_off;
    private boolean online;
}
