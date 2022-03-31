package com.doit.study.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    private String result;

    @Value("${spring.mail.title}")
    private String title;

    //인증 메일 전송 메서드
    public String mailSend(String email) {
        int key = (int)((Math.random()* (99999 - 10000 + 1)) + 10000);
        log.info("key = "+ key);

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(title);
            log.info("title = "+title);
            simpleMailMessage.setText("\"귀하의 이메일 인증번호는 \"" + key + "\" 입니다. 인증번호를 복사하여 입력해주세요.\"");
            javaMailSender.send(simpleMailMessage);
            result = Integer.toString(key);
            log.info("result = " + result);
        } catch (Exception e) {
            result = "error";
        }
        return result;
    }


}