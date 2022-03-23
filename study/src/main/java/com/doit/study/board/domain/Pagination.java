package com.doit.study.board.domain;

import lombok.Data;

@Data
public class Pagination {

    // 현재 페이지
    private int currentPage;
    // 한 페이지당 게시물 갯수
    public final int countPerPage = 3;
    // 화면 하단 페이지 사이즈
    private int pageSize;
    // 전체 데이터 개수
    private int totalRecordCount;
    // 전체 페이지 개수
    private int totalPageCount;
    // 페이지 리스트의 첫 페이지 번호
    private int firstPage;
    // 페이지 리스트의 마지막 페이지 번호
    private int lastPage;
    // SQL의 조건절에 사용되는 첫 RNUM
    private int firstRecordIndex;
    // SQL의 조건절에 사용되는 마지막 RNUM
    private int lastRecordIndex;
    // 이전 페이지 존재 여부
    private boolean hasPreviousPage;
    // 다음 페이지 존재 여부
    private boolean hasNextPage;

    public Pagination(int currentPage, int pageSize) {
        //강제입력방지
        if (currentPage < 1) {
            currentPage = 1;
        }
        // 하단 페이지 갯수 4개로 제한
        if (pageSize != 4) {
            pageSize = 4;
        }
        this.currentPage = currentPage;
        this.pageSize = pageSize;

    }

    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;

        if (totalRecordCount > 0) {
            calculation();
        }
    }

private void calculation() {

    // 전체 페이지 수 (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장)
    totalPageCount = ((totalRecordCount - 1) / countPerPage) + 1;
    if (this.getCurrentPage() > totalPageCount) {
        this.setCurrentPage(totalPageCount);
    }

    // 페이지 리스트의 첫 페이지 번호
    firstPage = ((this.getCurrentPage() - 1) / this.getPageSize()) * this.getPageSize() + 1;

    // 페이지 리스트의 마지막 페이지 번호 (마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장)
    lastPage = firstPage + this.getPageSize() - 1;
    if (lastPage > totalPageCount) {
        lastPage = totalPageCount;
    }

    // SQL의 조건절에 사용되는 첫 RNUM
    firstRecordIndex = (this.getCurrentPage()-1) * countPerPage; //3
    // SQL의 조건절에 사용되는 마지막 RNUM
    lastRecordIndex = this.getCurrentPage() * countPerPage;

    // 이전 페이지 존재 여부
    hasPreviousPage = firstPage == 1 ? false : true;
    if(hasPreviousPage == false) {
        if(currentPage != firstPage) {
            hasPreviousPage = true;
        }else {
            hasPreviousPage = false;
        }
    }

    // 다음 페이지 존재 여부
    hasNextPage = (lastPage * countPerPage) >= totalRecordCount ? false : true;
    if(hasNextPage == false) {
        if(currentPage != lastPage) {
            hasNextPage = true;
        }else {
            hasNextPage = false;
        }
    }
}
}