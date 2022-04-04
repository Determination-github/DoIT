package com.doit.study.board.domain;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
public class Board {


    private String study_id, user_id, schedule_start, schedule_end, title;

    private String content, address;

    private int moim_flag;

    private String interest1, interest2, interest3, sub_title;

    private Date reg_date;

    private int view_count, comment_count;

    @Builder
    public Board(String study_id, String user_id, String schedule_start, String schedule_end, String title,
                 String content, String address, int moim_flag, String interest1, String interest2, String interest3,
                 String sub_title) {
        this.study_id = study_id;
        this.user_id = user_id;
        this.schedule_start = schedule_start;
        this.schedule_end = schedule_end;
        this.title = title;
        this.content = content;
        this.address = address;
        this.moim_flag = moim_flag;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
        this.sub_title = sub_title;
    }

}
