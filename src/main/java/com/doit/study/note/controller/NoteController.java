package com.doit.study.note.controller;

import com.doit.study.note.dto.NoteDto;
import com.doit.study.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Controller
@RequiredArgsConstructor
@Slf4j
public class NoteController {

    private final NoteService noteService;

    /**
     * 노트 정보 가져오기
     * @param id
     * @param model
     * @param request
     * @param response
     * @return String
     * @throws IOException
     */
    @GetMapping("/note/{id}")
    public String profile(@PathVariable Integer id,
                          Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        //세션에서 아이디값 가져오기
        HttpSession session = request.getSession(false);
        Integer userId = (Integer)session.getAttribute("id");

        if(Objects.equals(id, userId)) { //아이디랑 노트 정보가 같은 경우
            //노트 정보 가져오기
            List<NoteDto> note = noteService.getNote(id);
            model.addAttribute("note", note);
            return "members/note";
        } else {
            throw new IOException();
        }
    }

    /**
     * 노트 정보 삭제하기
     * @param id
     * @param noteDto
     * @return ResponseEntity
     * @throws Exception
     */
    @DeleteMapping("/note/{id}")
    public ResponseEntity deleteNote(@PathVariable Integer id,
                                     @RequestBody NoteDto noteDto,
                                     HttpServletRequest request) throws Exception {

        //세션에서 아이디값 가져오기
        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("id");

        if(Objects.equals(id, userId)) { //아이디랑 노트 정보가 같은 경우
            noteService.deleteNote(noteDto.getNote_id());
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
