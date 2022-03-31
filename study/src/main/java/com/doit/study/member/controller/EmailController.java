package com.doit.study.member.controller;

import com.doit.study.member.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/join")
@RestController
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/mailCheck")
    public String mailCheck(@RequestParam("email") String email) {
        //메일에 보낸 key번호 반환
        String result = emailService.mailSend(email);
        return result;
    }
}
