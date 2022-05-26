package com.doit.study.member.service;

import com.doit.study.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final MemberMapper memberMapper;


    @Value("${spring.mail.title}")
    private String title;

    @Value("${spring.mail.temp}")
    private String tempTitle;

    //인증 메일 전송 메서드
    public String mailSend(String email) {
        int key = (int)((Math.random()* (99999 - 10000 + 1)) + 10000);
        log.info("key = "+ key);

        String result;

        try {
            log.info("보내는 메일 주소 : {}", email);
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

    //비밀번호 찾기용 이메일 확인 메서드
    public String findMemberByEmail(String email) {
        log.info("email={}", email);

        String result = memberMapper.findMemberByEmail(email);
        log.info("result={}", result);

        return result;
    }

    //인증 메일 전송 메서드
    public void sendTempPwd(String email) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거해 주었다.
        String password = uuid.substring(0, 10);
        log.info("password = "+ password);

        try {
            log.info("보내는 메일 주소 : {}", email);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(tempTitle);
            log.info("tempTitle = "+tempTitle);
            simpleMailMessage.setText("\"귀하의 임시 비밀번호는 \"" + password + "\" 입니다. 인증번호를 복사하여 입력해주세요.\"");
            javaMailSender.send(simpleMailMessage);

            //db 비밀번호 변경
            memberMapper.updatePwdById(email, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}