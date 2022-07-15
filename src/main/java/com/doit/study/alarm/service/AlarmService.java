package com.doit.study.alarm.service;

import com.doit.study.alarm.dto.AlarmDto;

import java.util.List;

public interface AlarmService {

    //알람 저장
    void saveAlarm(AlarmDto alarmDto);

    //알람 가져오기
    List<AlarmDto> getAlarm(Integer id);

    //알람 삭제
    void deleteAlarm(Integer id);
}
