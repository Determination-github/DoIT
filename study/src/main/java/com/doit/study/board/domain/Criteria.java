package com.doit.study.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criteria {

    /** 현재 페이지 번호 */
    private int currentPageNo;

    /** 페이지당 출력할 데이터 개수 */
    private int recordsPerPage;

    /** 화면 하단에 출력할 페이지 사이즈 */
    private int pageSize;

    /** 검색 키워드 */
    private String searchKeyword;

    /** 검색 유형 */
    private String searchType;

    public Criteria() {
        this.currentPageNo = 1;
        this.recordsPerPage = 10;
        this.pageSize = 10;
    }

    public int getFirstRecordIndex() {
        return (currentPageNo - 1) * recordsPerPage;
    }
    public int getLastRecordIndex(){
        return currentPageNo * recordsPerPage;
    }
}
