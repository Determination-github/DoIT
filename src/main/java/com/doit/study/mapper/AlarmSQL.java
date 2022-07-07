package com.doit.study.mapper;

public class AlarmSQL {

    public static final String saveAlarm =
            "INSERT INTO ALARM_TB(user_id, gubun, message, url)\n" +
                    "VALUES(#{alarm.user_id}, #{alarm.gubun}, #{alarm.message}, #{alarm.url})";

    public static final String getAlarm =
            "SELECT * FROM ALARM_TB WHERE user_id = #{user_id} " +
                    "ORDER BY reg_date ASC";

    public static final String deleteAlarm =
            "DELETE FROM ALARM_TB " +
                    "WHERE alarm_id = #{alarm_id} ";

}
