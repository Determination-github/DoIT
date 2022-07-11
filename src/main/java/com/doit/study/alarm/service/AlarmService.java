package com.doit.study.alarm.service;

import com.doit.study.alarm.dto.AlarmDto;

import java.util.List;

public interface AlarmService {

    //알람 저장
    void saveAlarm(AlarmDto alarmDto) throws Exception;

    //알람 가져오기
    List<AlarmDto> getAlarm(Integer id) throws Exception;

    //알람 삭제
    void deleteAlarm(Integer id) throws Exception;
}
