package com.doit.study.board.dto;

//import com.doit.study.Board.domain.Board;
import lombok.*;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
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

}
