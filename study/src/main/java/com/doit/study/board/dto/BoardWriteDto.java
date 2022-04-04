package com.doit.study.board.dto;

import com.doit.study.board.domain.Board;
import lombok.Data;

import java.util.Date;

@Data
public class BoardWriteDto {
    private String  board_id, board_writerId;
    private String  board_startDate, board_endDate;
    private String  board_title, board_subTitle, board_content;
    private String  board_location, board_hashtag;
    private int     board_viewCount, board_commentCount, board_onOffline;
    private String  writer_nickName;
    private String  writer_interest1, writer_interest2, writer_interest3;
    private Date board_regDate;

    //첫 번째 뷰페이지에서 넘어온 데이터 합치기
    public BoardWriteDto toBoardWriteDto(FirstStudyDto firstStudyDto, BoardWriteDto boardWriteDto) {

        //boardWriteDto 값 세팅
        boardWriteDto.setWriter_nickName(firstStudyDto.getNickName());
        boardWriteDto.setWriter_interest1(firstStudyDto.getInterest1());
        boardWriteDto.setWriter_interest1(firstStudyDto.getInterest2());
        boardWriteDto.setWriter_interest1(firstStudyDto.getInterest3());
        boardWriteDto.setBoard_writerId(firstStudyDto.getWriterId());
        boardWriteDto.setBoard_startDate(firstStudyDto.getStartDate());
        boardWriteDto.setBoard_endDate(firstStudyDto.getEndDate());
        boardWriteDto.setBoard_location(firstStudyDto.getLocation());
        boardWriteDto.setBoard_onOffline(firstStudyDto.getOnOffLine());
        boardWriteDto.setBoard_subTitle(firstStudyDto.getSubTitle());

        return boardWriteDto;
    }

    public Board toEntity(String uuid, BoardWriteDto boardWriteDto) {
        return Board.builder()
                .study_id(uuid)
                .user_id(board_writerId)
                .schedule_start(board_startDate)
                .schedule_end(board_endDate)
                .title(board_title)
                .content(board_content)
                .address(board_location)
                .interest1(writer_interest1)
                .interest2(writer_interest2)
                .interest3(writer_interest3)
                .sub_title(board_subTitle)
                .moim_flag(board_onOffline)
                .build();
    }






}
