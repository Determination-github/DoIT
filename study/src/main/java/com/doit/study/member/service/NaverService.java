package com.doit.study.member.service;

import com.github.scribejava.core.model.OAuth2AccessToken;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

public interface NaverService {

    String generateState();

    String getAuthorizationUrl(HttpSession session);

    OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException;

    String getUserProfile(OAuth2AccessToken oauthToken) throws IOException;

    HashMap<String, String> getNaverUserInfo(String apiResult) throws ParseException;
}
