package com.doit.study.member.service;

import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.NaverDto;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

public interface NaverService {

    //state값 생성
    String generateState();

    //콜백을 위한 url 생성
    String getAuthorizationUrl(HttpSession session);

    //access token 발급 받기
    OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException;

    //발급 받은 access token을 이용해 회원 정보 가져오기
    String getUserProfile(OAuth2AccessToken oauthToken) throws IOException;

    //유저정보 Map형태로 저장하기
    HashMap<String, String> getNaverUserInfo(String apiResult) throws ParseException;

    //회원가입 - Naver 로그인 회원
    NaverDto joinSocial(NaverDto naverDto);

    //네이버 로그인 회원의 회원가입 여부 확인
    NaverDto findSocialMember(String id);

}
