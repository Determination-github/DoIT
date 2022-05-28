package com.doit.study.board.dto;

import com.doit.study.board.domain.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@NoArgsConstructor
public class BoardDto {
    private int  board_id, board_writerId;
    private String  writer_nickName, board_location;
    private int     board_on_off, board_viewCount, board_commentCount;
    private Date board_regDate;
    private boolean board_onOffLine;

    //프로필 이미지
    private String path;

    @NotEmpty(message = "날짜를 입력해주세요.")
    private String  board_startDate, board_endDate;

    @NotEmpty(message = "제목을 입력해주세요.")
    private String board_title;

    private String  board_location1, board_location2;

    @NotEmpty(message = "카테고리를 모두 선택해주세요.")
    private String  board_interest1, board_interest2, board_interest3;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String board_content;


    public Board toEntity(BoardDto boardWriteDto) {
        return Board.builder()
                .study_id(board_id)
                .id(board_writerId)
                .schedule_start(board_startDate)
                .schedule_end(board_endDate)
                .title(board_title)
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
