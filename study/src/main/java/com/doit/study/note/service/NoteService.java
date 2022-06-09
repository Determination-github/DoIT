package com.doit.study.note.service;

import com.doit.study.note.dto.NoteDto;

import java.util.List;

public interface NoteService {

    void saveNote(NoteDto noteDto);

    List<NoteDto> getNote(Integer id);

    void deleteNote(Integer note_id);
}
