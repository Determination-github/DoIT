package com.doit.study.mapper;

import com.doit.study.alarm.domain.Alarm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AlarmMapper {

    //알람 정보 저장
    @Insert(AlarmSQL.saveAlarm)
    void saveAlarm(@Param("alarm") Alarm alarm);

    //알람 정보 가져오기
    @Select(AlarmSQL.getAlarm)
    List<Alarm> getAlarm(@Param("user_id") Integer id);

    //읽음 처리
    @Update(AlarmSQL.deleteAlarm)
    void deleteAlarm(@Param("alarm_id") Integer id);
}
