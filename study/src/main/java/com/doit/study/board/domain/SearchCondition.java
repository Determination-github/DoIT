package com.doit.study.board.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCondition {

    private Integer currentPage = 1;
    private Integer pageSize = 4;
    private Integer firstRecordIndex = 0;
    private Integer lastRecordIndex = 3;
    private String  board_Title = "";



    public String getQueryString(Integer currentPage){
        // ?currentPage=1&pageSize=4&board_Title="title"
        return UriComponentsBuilder.newInstance()
                .queryParam("currentPage",  currentPage)
                .queryParam("pageSize", pageSize)
                .queryParam("board_Title",  board_Title)
                .build().toString();
    }



    public String getQueryString(){
        return getQueryString(currentPage);
    }
}