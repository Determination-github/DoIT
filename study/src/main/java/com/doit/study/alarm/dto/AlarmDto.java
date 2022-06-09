package com.doit.study.alarm.dto;


import com.doit.study.alarm.domain.Alarm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlarmDto {

    private Integer alarm_id;
    private Integer receiver_id;
    private Integer gubun;
    private String message;
    private String url;
    private Integer read_yn;
    private Date reg_date;

    public AlarmDto(Integer receiver_id,
                    Integer gubun,
                    String message,
                    String url) {
        this.receiver_id = receiver_id;
        this.gubun = gubun;
        this.message = message;
        this.url = url;
    }

    public Alarm toEntity(AlarmDto alarmDto) {
        return Alarm.builder()
                .user_id(receiver_id)
                .gubun(gubun)
                .message(message)
                .url(url)
                .build();
    }

    public AlarmDto toDto(Alarm alarm) {
        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setAlarm_id(alarm.getAlarm_id());
        alarmDto.setReceiver_id(alarm.getUser_id());
        alarmDto.setMessage(alarm.getMessage());
        alarmDto.setUrl(alarm.getUrl());
        return alarmDto;
    }

    public String makeMsg(int gubun, String title) {
        if(gubun == 0) {
            title = "[쪽지], " + HtmlUtils.htmlEscape(title);
        } else {
            title = "[댓글], " + HtmlUtils.htmlEscape(title);
        }

        return title;
    }

}
