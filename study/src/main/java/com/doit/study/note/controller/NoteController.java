package com.doit.study.note.controller;

import com.doit.study.note.dto.NoteDto;
import com.doit.study.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/note/{id}")
    public String profile(@PathVariable Integer id, Model model) {

        //노트 정보 가져오기
        List<NoteDto> note = noteService.getNote(id);
        model.addAttribute("note", note);

        return "/members/note";
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity deleteNote(@PathVariable Integer id,
                                     @RequestBody NoteDto noteDto) {

        noteService.deleteNote(noteDto.getNote_id());

        return ResponseEntity.ok(id);
    }
}
