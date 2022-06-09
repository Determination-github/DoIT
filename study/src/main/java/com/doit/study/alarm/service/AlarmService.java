package com.doit.study.alarm.service;

import com.doit.study.alarm.dto.AlarmDto;

import java.util.List;

public interface AlarmService {

    void saveAlarm(AlarmDto alarmDto);

    List<AlarmDto> getAlarm(Integer id);

    void deleteAlarm(Integer id);
}
