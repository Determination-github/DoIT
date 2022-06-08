package com.doit.study.note.controller;

import com.doit.study.note.domain.Alarm;
import com.doit.study.note.domain.Note;
import com.doit.study.note.dto.NoteDto;
import com.doit.study.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class NoteController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private final NoteService noteService;

    @MessageMapping("/send/{id}")
    public void noteAlarm(@DestinationVariable("id") Integer id,
                         NoteDto noteDto) throws Exception {
        Thread.sleep(1000); // simulated delay

        log.info("noteDto={}", noteDto);

        noteService.saveNote(noteDto);

        String content = "[쪽지], " + HtmlUtils.htmlEscape(noteDto.getTitle());
        String url = "/note/"+noteDto.getReceiver_id();

        simpMessagingTemplate.convertAndSend("/note/receiving/" + noteDto.getReceiver_id() , new Alarm(content, url));
    }

    @GetMapping("/note/{id}")
    public String profile(@PathVariable Integer id, Model model) {

        //노트 정보 가져오기
        List<NoteDto> note = noteService.getNote(id);
        model.addAttribute("note", note);

        return "/members/note";
    }

    @PutMapping("/note/{id}")
    public ResponseEntity updateReadYN(@PathVariable Integer id) {

        log.info("note_id={}", id);

        Integer result = noteService.updateReadYN(id);
        if(result!= null) {
            return ResponseEntity.ok(id);
        } else {
            return null;
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity deleteNote(@PathVariable Integer id,
                                     @RequestBody NoteDto noteDto) {

        noteService.deleteNote(noteDto.getNote_id());

        return ResponseEntity.ok(id);
    }
}
