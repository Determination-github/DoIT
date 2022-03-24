package com.doit.study.board.domain;

import org.springframework.web.util.UriComponentsBuilder;

public class SearchCondition {
        private Integer currentPage = 1;
        private Integer pageSize = 4;
        private String  board_Title = "";


        public SearchCondition(){}

        public SearchCondition(Integer currentPage, Integer pageSize, String board_Title) {
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.board_Title = board_Title;
        }

    public SearchCondition(int currentPage, int pageSize) {
        this(currentPage, pageSize, "");
    }

    public String getQueryString() {
            return getQueryString(currentPage);
        }

        public String getQueryString(Integer currentPage) {
            return UriComponentsBuilder.newInstance()
                    .queryParam("currentPage",  currentPage)
                    .queryParam("pageSize", pageSize)
                    .queryParam("board_Title",  board_Title)
                    .build().toString();
        }
}
