package com.doit.study.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private int study_id, id, on_off, view_count, comment_count;

    private String schedule_start, schedule_end, title, content, sub_title;

    private String location, interest1, interest2, interest3;

    private Date reg_date;

    @Builder
    public Board(int id, String schedule_start, String schedule_end, String title, String sub_title,
                 String content, String location, int on_off, String interest1, String interest2, String interest3) {
        this.id = id;
        this.schedule_start = schedule_start;
        this.schedule_end = schedule_end;
        this.title = title;
        this.sub_title = sub_title;
        this.content = content;
        this.location = location;
        this.on_off = on_off;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
    }
}
