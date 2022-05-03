package com.doit.study.board.dto;

import com.doit.study.board.domain.Board;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
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
        boardWriteDto.setWriter_interest2(firstStudyDto.getInterest2());
        boardWriteDto.setWriter_interest3(firstStudyDto.getInterest3());
        boardWriteDto.setBoard_writerId(firstStudyDto.getWriterId());
        boardWriteDto.setBoard_startDate(firstStudyDto.getStartDate());
        boardWriteDto.setBoard_endDate(firstStudyDto.getEndDate());
        boardWriteDto.setBoard_location(firstStudyDto.getTotalLocation());
        boardWriteDto.setBoard_onOffline(firstStudyDto.getFlag());
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

    @Builder
    public BoardWriteDto(String board_id, String board_writerId, String board_startDate, String board_endDate, String board_title, String board_subTitle, String board_content, String board_location, String board_hashtag, int board_viewCount, int board_commentCount, int board_onOffline, String writer_nickName, String writer_interest1, String writer_interest2, String writer_interest3, Date board_regDate) {
        this.board_id = board_id;
        this.board_writerId = board_writerId;
        this.board_startDate = board_startDate;
        this.board_endDate = board_endDate;
        this.board_title = board_title;
        this.board_subTitle = board_subTitle;
        this.board_content = board_content;
        this.board_location = board_location;
        this.board_hashtag = board_hashtag;
        this.board_viewCount = board_viewCount;
        this.board_commentCount = board_commentCount;
        this.board_onOffline = board_onOffline;
        this.writer_nickName = writer_nickName;
        this.writer_interest1 = writer_interest1;
        this.writer_interest2 = writer_interest2;
        this.writer_interest3 = writer_interest3;
        this.board_regDate = board_regDate;
    }

    public BoardWriteDto toBoardWriteDto(Board board) {
        return BoardWriteDto.builder()
                .board_id(board.getStudy_id())
                .board_writerId(board.getUser_id())
                .board_startDate(board.getSchedule_start())
                .board_endDate(board.getSchedule_end())
                .board_title(board.getTitle())
                .board_content(board.getContent())
                .board_subTitle(board.getSub_title())
                .board_location(board.getAddress())
                .board_regDate(board.getReg_date())
                .board_onOffline(board.getMoim_flag())
                .board_viewCount(board.getView_count())
                .board_commentCount(board.getComment_count())
                .writer_interest1(board.getInterest1())
                .writer_interest2(board.getInterest2())
                .writer_interest3(board.getInterest3())
                .build();
    }
}
