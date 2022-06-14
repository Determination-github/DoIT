package com.doit.study.alarm.controller;


import com.doit.study.alarm.dto.AlarmDto;
import com.doit.study.alarm.service.AlarmService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AlarmController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private final NoteService noteService;
    private final AlarmService alarmService;

    @MessageMapping("/note/{id}")
    public void noteAlarm(@DestinationVariable("id") Integer id,
                          NoteDto noteDto) throws Exception {
        Thread.sleep(1000); // simulated delay

        log.info("noteDto={}", noteDto);

        noteService.saveNote(noteDto);

        //url 설정
        String url = "/note/" + noteDto.getReceiver_id();

        AlarmDto alarmDto = new AlarmDto(noteDto.getReceiver_id(), 0, noteDto.getTitle(), url);
        alarmService.saveAlarm(alarmDto);

        simpMessagingTemplate.convertAndSend("/alarm/receiving/" + noteDto.getReceiver_id() , alarmDto);
    }

    @GetMapping("/alarm/{id}")
    public ResponseEntity getAlarm(@PathVariable Integer id,
                                   Model model) throws Exception {

        log.info("실행됨");
        List<AlarmDto> alarmDtoList = alarmService.getAlarm(id);
        if(!alarmDtoList.isEmpty()) {
            model.addAttribute("alarmList", alarmDtoList);
        }

        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/alarm/{id}")
    public ResponseEntity deleteAlarm(@PathVariable Integer id,
                                      HttpServletRequest request) throws Exception {

        log.info("alarm_id={}", id);

        alarmService.deleteAlarm(id);

        return ResponseEntity.ok(id);
    }

    @MessageMapping("/comment/{id}")
    public void commentAlarm(@DestinationVariable("id") Integer id,
                             AlarmDto alarmDto) throws Exception {
        Thread.sleep(1000); // simulated delay

        log.info("alarmDto={}", alarmDto);

        //url 설정
        String url = "/board/result/" + alarmDto.getStudy_id();
        alarmDto.setUrl(url);

        //gubun 설정
        alarmDto.setGubun(1);

        alarmService.saveAlarm(alarmDto);

        simpMessagingTemplate.convertAndSend("/alarm/receiving/" + alarmDto.getReceiver_id() , alarmDto);
    }

}
