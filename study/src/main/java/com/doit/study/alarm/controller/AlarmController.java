package com.doit.study.alarm.controller;


import com.doit.study.alarm.dto.AlarmDto;
import com.doit.study.alarm.service.AlarmService;
import com.doit.study.note.dto.NoteDto;
import com.doit.study.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    /**
     * 알람 메시지 전송
     * @param id
     * @param noteDto
     * @throws Exception
     */
    @MessageMapping("/note/{id}")
    public void noteAlarm(@DestinationVariable("id") Integer id,
                          NoteDto noteDto) throws Exception {
        Thread.sleep(1000); // simulated delay

        //쪽지 정보 저장
        noteService.saveNote(noteDto);

        //url 설정
        String url = "/note/" + noteDto.getReceiver_id();

        //알람 dto 생성
        AlarmDto alarmDto = new AlarmDto(noteDto.getReceiver_id(), 0, noteDto.getTitle(), url);

        //알람 저장
        alarmService.saveAlarm(alarmDto);

        //메시지 보내기
        simpMessagingTemplate.convertAndSend("/alarm/receiving/" + noteDto.getReceiver_id() , alarmDto);
    }

    /**
     * 알람 정보 가져오기기
     *@param id
     * @param model
     * @return ResponseEntity
     * @throws Exception
     */
    @GetMapping("/alarm/{id}")
    public ResponseEntity getAlarm(@PathVariable Integer id,
                                   Model model) throws Exception {

        List<AlarmDto> alarmDtoList = alarmService.getAlarm(id);

        if(!alarmDtoList.isEmpty()) {
            model.addAttribute("alarmList", alarmDtoList);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 알람 메시지 삭제
     * @param id
     * @param request
     * @return ResponseEntity
     * @throws Exception
     */
    @DeleteMapping("/alarm/{id}")
    public ResponseEntity deleteAlarm(@PathVariable Integer id,
                                      HttpServletRequest request) throws Exception {

        alarmService.deleteAlarm(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 댓글 알림
     * @param id
     * @param alarmDto
     * @throws Exception
     */
    @MessageMapping("/comment/{id}")
    public void commentAlarm(@DestinationVariable("id") Integer id,
                             AlarmDto alarmDto) throws Exception {
        Thread.sleep(1000); // simulated delay

        //url 설정
        String url = "/board/result/" + alarmDto.getStudy_id();
        alarmDto.setUrl(url);

        //gubun 설정
        alarmDto.setGubun(1);

        //댓글 알람 저장
        alarmService.saveAlarm(alarmDto);

        //댓글 알림 보내기
        simpMessagingTemplate.convertAndSend("/alarm/receiving/" + alarmDto.getReceiver_id() , alarmDto);
    }

}
