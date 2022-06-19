package com.doit.study.member.service;

import com.doit.study.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

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

    /***
     * 인증 메일 전송 메서드
     * @param email
     * @return String
     */
    public String mailSend(String email) {
        //인증용 랜덤키 생성
        int key = (int)((Math.random()* (99999 - 10000 + 1)) + 10000);

        String result;

        try {
            //이메일 전송 객체 생성
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            //정보 설정
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText("\"귀하의 이메일 인증번호는 \"" + key + "\" 입니다. 인증번호를 복사하여 입력해주세요.\"");

            //메일 보내기
            javaMailSender.send(simpleMailMessage);
            result = Integer.toString(key);
        } catch (Exception e) {
            result = "error";
            log.error("error = " + e);
        }
        return result;
    }

    /***
     * 비밀번호 찾기용 이메일 확인
     * @param email
     * @return String
     */
    public String findMemberByEmail(String email) {
        return memberMapper.findMemberByEmail(email);
    }

    /***
     * 임시 비밀번호 발급
     * @param email
     */
    public void sendTempPwd(String email) {
        //임시비밀번호 생성
        String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거해 주었다.
        String password = uuid.substring(0, 10);

        //메일 전송 객체 생성
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        //메일 정보 설정
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(tempTitle);
        simpleMailMessage.setText("\"귀하의 임시 비밀번호는 \"" + password + "\" 입니다. 인증번호를 복사하여 입력해주세요.\"");

        //메일 전송
        javaMailSender.send(simpleMailMessage);

        //db 비밀번호 변경
        memberMapper.updatePwdById(email, password);
    }
}