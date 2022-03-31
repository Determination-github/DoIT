package com.doit.study.member.service;

import com.doit.study.member.dto.KakaoDto;
import com.doit.study.member.dto.MemberDto;

import java.util.HashMap;

public interface KakaoService {

    //카카오 callback url 생성
    String getKaKaoCallbackUrl();

    //카카오 access token 발급
    String getAccessKakaoToken(String authorize_code);

    //발급받은 access token으로 회원 정보 가졍괴
    HashMap<String, String> getKaKaoUserInfo(String access_Token);

    //회원 정보 삭제
    void unlinkKakao(String access_Token);

    //회원가입 - Kakao 로그인 회원
    KakaoDto joinSocial(KakaoDto kakaoDto);

    //카카오 로그인 회원의 회원가입 여부 확인
    KakaoDto findSocialMember(String id);

    MemberDto kakaoToMember(KakaoDto kakaoDto);
}
