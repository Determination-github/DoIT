package com.doit.study.mapper;

public class NoteSQL {

    public static final String saveSenderNote =
        "INSERT INTO NOTE_TB(user_id, sender_id, receiver_id, title, content)\n" +
                "VALUES(#{note.user_id}, #{note.user_id}, #{note.receiver_id}, " +
                "#{note.title}, #{note.content})";

    public static final String saveReceiverNote =
        "INSERT INTO NOTE_TB(user_id, sender_id, receiver_id, title, content)\n" +
                "VALUES(#{note.receiver_id}, #{note.user_id}, #{note.receiver_id}, " +
                "#{note.title}, #{note.content})";

    public static final String getAlarm =
        "SELECT * FROM NOTE_TB WHERE receiver_id = #{receiver_id} " +
                "AND user_id = #{receiver_id}  " +
                "AND read_yn = 0 " +
                "ORDER BY reg_date";

    public static final String getNote =
        "SELECT * FROM NOTE_TB " +
                "WHERE user_id = #{id} " +
                "AND gubun = 0 " +
                "ORDER BY reg_date";

    public static final String updateReadYN =
        "UPDATE NOTE_TB SET read_yn = 1 " +
                "WHERE note_id = #{note_id} ";


    public static final String deleteNote =
        "DELETE FROM NOTE_TB WHERE note_id = #{note_id}";

}
