package com.doit.study.member.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private String result;

    @Value("${spring.mail.title}")
    private String title;

    public String mailSend(String email) {
        int key = (int)((Math.random()* (99999 - 10000 + 1)) + 10000);

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText("\"귀하의 이메일 인증번호는 \"" + key + "\" 입니다. 인증번호를 복사하여 입력해주세요.\"");
            javaMailSender.send(simpleMailMessage);
            result = Integer.toString(key);
        } catch (Exception e) {
            result = "error";
        }
        return result;
    }


}