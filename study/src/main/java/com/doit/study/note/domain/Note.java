package com.doit.study.note.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    private Integer note_id, user_id, sender_id, receiver_id;
    private String title, content;
    private Integer gubun, read_yn;
    private Date reg_date;

    @Builder Note(int user_id, int receiver_id, String title, String content, int gubun) {
        this.user_id = user_id;
        this.receiver_id = receiver_id;
        this.title = title;
        this.content = content;
        this.gubun = gubun;
    }
}
