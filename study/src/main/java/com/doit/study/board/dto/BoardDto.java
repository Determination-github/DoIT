package com.doit.study.board.dto;

import lombok.*;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
public class BoardDto {
    private int     board_id;
    private String  board_writerId;
    private String  board_title;
    private String  board_subTitle;
    private String  board_content;
    private String  board_location;
    private String  board_onOffline;
    private int     board_viewCount;
    private int     board_commentCount;
    private String  board_startDate;
    private String  board_endDate;
    private Date    board_regDate;
    private String  board_hashtag;

//
//    public BoardDto(String board_writerId, String board_startDate, String board_endDate,
//                    String board_location, String board_onOffline, String board_subTitle) {
//        this.board_writerId = board_writerId;
//        this.board_startDate = board_startDate;
//        this.board_endDate = board_endDate;
//        this.board_location = board_location;
//        this.board_onOffline = board_onOffline;
//        this.board_subTitle = board_subTitle;
//    }
//
//    public BoardDto fsDtoToBoardDto(FirstStudyDto firstStudyDto) {
//        BoardDto boardDto = new BoardDto();
//        this.board_writerId = firstStudyDto.getWriterId();
//        this.board_startDate = firstStudyDto.getStartDate();
//        this.board_endDate = firstStudyDto.getEndDate();
//        this.board_location = firstStudyDto.getLocation();
//        this.board_onOffline = firstStudyDto.getOnOffLine();
//        this.board_subTitle = firstStudyDto.getSubTitle();
//        return boardDto;
//    }

    private String  board_File;
    private String  board_FilePath;

    private Integer currentPage = 1;
    private Integer pageSize = 4;
    private Integer firstRecordIndex = 1;
    private Integer lastRecordIndex = 3;
    // 한 페이지당 게시물 갯수
    public final int countPerPage = 3;
    // 전체 데이터 개수
    private int totalRecordCount;
    // 전체 페이지 개수
    private int totalPageCount;
    // 페이지 리스트의 첫 페이지 번호
    private int firstPage;
    // 페이지 리스트의 마지막 페이지 번호
    private int lastPage;
    // 이전 페이지 존재 여부
    private boolean hasPreviousPage;
    // 다음 페이지 존재 여부
    private boolean hasNextPage;

    public BoardDto(){
        new BoardDto(1, 4);
    }

    public BoardDto(int currentPage, int pageSize) {
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
        this.calculation();
    }

    public BoardDto(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;

        doPaging(totalRecordCount);
    }


    public void doPaging(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;

        if (totalRecordCount > 0) {
            this.calculation();
        }
    }


    public void calculation() {

        // 전체 페이지 수 (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장)
        this.totalPageCount = ((totalRecordCount - 1) / this.getPageSize()) + 1;
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
        firstRecordIndex = (this.getCurrentPage() - 1) * countPerPage; //3
        // SQL의 조건절에 사용되는 마지막 RNUM
        lastRecordIndex = this.getCurrentPage() * countPerPage;

        // 이전 페이지 존재 여부
        hasPreviousPage = firstPage == 1 ? false : true;
        if (hasPreviousPage == false) {
            if (currentPage != firstPage) {
                hasPreviousPage = true;
            } else {
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

