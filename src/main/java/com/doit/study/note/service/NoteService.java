package com.doit.study.note.service;

import com.doit.study.note.dto.NoteDto;

import java.util.List;

public interface NoteService {

    //노트 저장
    void saveNote(NoteDto noteDto) throws Exception;

    //노트 정보 가져오기
    List<NoteDto> getNote(Integer id);

    //노트 삭제
    void deleteNote(Integer note_id) throws Exception;
}
