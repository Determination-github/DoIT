package com.doit.study.member.service;

import java.util.HashMap;

public interface KakaoService {

    //카카오 callback url 생성
    String getKaKaoCallbackUrl();

    String getAccessKakaoToken(String authorize_code);

    HashMap<String, String> getKaKaoUserInfo(String access_Token);

    void unlinkKakao(String access_Token);
}
