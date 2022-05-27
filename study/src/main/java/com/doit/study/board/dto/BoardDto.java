package com.doit.study.board.dto;

import com.doit.study.board.domain.Board;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BoardDto {
    private int  board_id, board_writerId;
    private String  writer_nickName;
    private String  board_startDate, board_endDate;
    private String  board_title, board_subTitle, board_content, board_location;
    private int     board_on_off, board_viewCount, board_commentCount;
    private String  board_interest1, board_interest2, board_interest3;
    private Date board_regDate;

    //프로필 이미지
    private String path;

//    첫 번째 글 작성 페이지에서 넘어온 데이터 합치기
    public BoardDto toBoardDto(FirstStudyDto firstStudyDto, BoardDto boardDto) {

        //boardDto 값 세팅
        boardDto.setWriter_nickName(firstStudyDto.getNickName());
        boardDto.setBoard_interest1(firstStudyDto.getInterest1());
        boardDto.setBoard_interest2(firstStudyDto.getInterest2());
        boardDto.setBoard_interest3(firstStudyDto.getInterest3());
        boardDto.setBoard_writerId(firstStudyDto.getId());
        boardDto.setBoard_startDate(firstStudyDto.getStartDate());
        boardDto.setBoard_endDate(firstStudyDto.getEndDate());
        boardDto.setBoard_location(firstStudyDto.getTotalLocation());
        boardDto.setBoard_on_off(firstStudyDto.getFlag());
        boardDto.setBoard_subTitle(firstStudyDto.getSubTitle());

        return boardDto;
    }

    public Board toEntity(BoardDto boardWriteDto) {
        return Board.builder()
                .id(board_writerId)
                .schedule_start(board_startDate)
                .schedule_end(board_endDate)
                .title(board_title)
                .sub_title(board_subTitle)
                .content(board_content)
                .location(board_location)
                .interest1(board_interest1)
                .interest2(board_interest2)
                .interest3(board_interest3)
                .on_off(board_on_off)
                .build();
    }


    public BoardDto toBoardDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoard_id(board.getStudy_id());
        boardDto.setBoard_writerId(board.getId());
        boardDto.setBoard_startDate(board.getSchedule_start());
        boardDto.setBoard_endDate(board.getSchedule_end());
        boardDto.setBoard_title(board.getTitle());
        boardDto.setBoard_subTitle(board.getSub_title());
        boardDto.setBoard_content(board.getContent());
        boardDto.setBoard_location(board.getLocation());
        boardDto.setBoard_on_off(board.getOn_off());
        boardDto.setBoard_interest1(board.getInterest1());
        boardDto.setBoard_interest2(board.getInterest2());
        boardDto.setBoard_interest3(board.getInterest3());
        boardDto.setBoard_regDate(board.getReg_date());
        boardDto.setBoard_viewCount(board.getView_count());

        return boardDto;
    }
}
