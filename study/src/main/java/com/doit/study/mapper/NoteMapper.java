package com.doit.study.mapper;

import com.doit.study.note.domain.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface NoteMapper {

    //보내는 사람 노트 저장
    @Insert(NoteSQL.saveSenderNote)
    void saveSenderNote(@Param("note") Note note);

    //받는 사람 노트 저장
    @Insert(NoteSQL.saveReceiverNote)
    void saveReceiverNote(@Param("note") Note note);

    //알람 정보 가져오기
    @Select(NoteSQL.getAlarm)
    List<Note> getAlarm(@Param("receiver_id") Integer id);

    //노트 정보 가져오기
    @Select(NoteSQL.getNote)
    List<Note> getNote(@Param("id") Integer id);

    //읽음 처리
    @Update(NoteSQL.updateReadYN)
    Integer updateReadYN(@Param("note_id") Integer id);

    //삭제 처리
    @Delete(NoteSQL.deleteNote)
    void deleteNote(@Param("note_id") Integer id);
}
