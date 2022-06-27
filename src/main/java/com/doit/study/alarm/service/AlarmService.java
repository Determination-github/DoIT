package com.doit.study.alarm.service;

import com.doit.study.alarm.dto.AlarmDto;

import java.util.List;

public interface AlarmService {

    void saveAlarm(AlarmDto alarmDto) throws Exception;

    List<AlarmDto> getAlarm(Integer id) throws Exception;

    void deleteAlarm(Integer id) throws Exception;
}
