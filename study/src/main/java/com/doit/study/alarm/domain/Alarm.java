package com.doit.study.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {

    private Integer alarm_id, user_id, gubun;
    private String message, url;
    private Date reg_date;

    @Builder
    Alarm(Integer user_id, Integer gubun, String message, String url) {
        this.user_id = user_id;
        this.gubun = gubun;
        this.message = message;
        this.url = url;
    }
}
