package com.doit.study.member.service;

public interface EmailService {

    String mailSend(String email);

    String findMemberByEmail(String email);

    void sendTempPwd(String email);
}
