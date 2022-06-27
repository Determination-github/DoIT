package com.doit.study.member.service;

import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.SocialDto;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

public interface KakaoService {

    //카카오 callback url 생성
    String getKaKaoCallbackUrl();

    //카카오 access token 발급
    String getAccessKakaoToken(String authorize_code);

    //발급받은 access token으로 회원 정보 가졍괴
    HashMap<String, String> getKaKaoUserInfo(String access_Token) throws IOException, ParseException;

    //회원 정보 삭제
    void unlinkKakao(String access_Token) throws IOException;

    //카카오 로그인 회원의 회원가입 여부 확인
    MemberDto findSocialMember(String id);
}
