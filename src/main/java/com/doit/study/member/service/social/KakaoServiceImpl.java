package com.doit.study.member.service.social;

import com.doit.study.mapper.MemberMapper;
import com.doit.study.member.domain.Member;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.social.KakaoLoginApi;
import com.doit.study.member.service.SocialService;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:login.properties")
@PropertySource("classpath:kakao_secret.properties")
public class KakaoServiceImpl implements SocialService {

    private final MemberMapper memberMapper;

    @Value("${kakao.login.client.id}")
    private String CLIENT_ID;

    @Value("${kakao.login.client.secret}")
    private String CLIENT_SECRET;

    @Value("${kakao.login.redirect.url}")
    private String REDIRECT_URI;

    @Value("${kakao.login.session.state}")
    private String SESSION_STATE;

    // 프로필 조회 API URL
    @Value("${kakao.login.profile.api.url}")
    private String PROFILE_API_URL;

    /**
     * session에 데이터 저장
     * @param session
     * @param state
     */
    private void setSession(HttpSession session, String state) {
        session.setAttribute(SESSION_STATE, state);
    }

    /**
     * 세션에서 데이터 가져오기
     * @param session
     * @return String
     */
    private String getSession(HttpSession session) {
        return (String) session.getAttribute(SESSION_STATE);
    }

    /**
     * State 생성
     * @return String
     */
    public String generateState()
    {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    /**
     * 카카오 아이디로 인증 URL 생성
     * @param session
     * @return
     */
    @Override
    public String getAuthorizationUrl(HttpSession session) {
        String state = generateState();

        setSession(session, state);

        OAuth20Service oauthService = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)
                .state(state)
                .build(KakaoLoginApi.instance());

        return oauthService.getAuthorizationUrl();
    }

    /**
     * 카카오 아이디로 Callback 처리 및 AccessToken 획득
     * @param session
     * @param code
     * @param state
     * @return OAuth2AccessToken
     * @throws IOException
     */
    public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) {

        // Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인
        String sessionState = getSession(session);
        if (StringUtils.pathEquals(sessionState, state)) {

            OAuth20Service oauthService = new ServiceBuilder()
                    .apiKey(CLIENT_ID)
                    .apiSecret(CLIENT_SECRET)
                    .callback(REDIRECT_URI)
                    .state(state)
                    .build(KakaoLoginApi.instance());

            // Scribe에서 제공하는 AccessToken 획득 기능으로 카카오 Access Token을 획득
            OAuth2AccessToken accessToken = null;
            try {
                accessToken = oauthService.getAccessToken(code);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            return accessToken;
        }
        return null;
    }

    /**
     * 카카오 로그인 회원 정보 가져오기
     * @param access_Token
     * @return HashMap<String, String>
     * @throws ParseException
     */
    @Override
    public HashMap<String, String> getSocialUserInfo(String access_Token) throws IOException, ParseException {

        //요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, String> userInfo = new HashMap<String, String>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        //요청에 필요한 Header에 포함될 내용
        conn.setRequestProperty("Authorization", "Bearer " + access_Token);

        int responseCode = conn.getResponseCode();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(result);
        JSONObject jsonObj = (JSONObject) obj;

        //고유 카카오 회원 아이디
        Long id = (Long) jsonObj.get("id");

        JSONObject properties_obj = (JSONObject)jsonObj.get("properties");
        JSONObject kakao_account_obj = (JSONObject)jsonObj.get("kakao_account");

        //회원정보 가져오기
        String nickname = (String)properties_obj.get("nickname");
        String email = (String)kakao_account_obj.get("email");
        String gender = (String)kakao_account_obj.get("gender");

        //회원정보 저장
        userInfo.put("accessToken", access_Token);
        userInfo.put("id", String.valueOf(id));
        userInfo.put("nickname", nickname);
        userInfo.put("email", email);
        userInfo.put("gender", gender);

        return userInfo;
    }

    /**
     * 카카오 회원 정보 삭제
     * @param access_Token
     */
    @Override
    public void deleteAccessToken(String access_Token) throws IOException {
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";

        //url 설정
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
        int responseCode = conn.getResponseCode();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String result = "";
        String line = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }
    }

    /**
     * 카카오 로그인한 회원 정보 찾기
     * @param id
     * @return MemberDto
     */
    @Override
    public MemberDto findSocialMember(String id) {
        //member객체 찾기
        Optional<Member> findMember = memberMapper.findSocialMemberBySocialId(id);

        if(findMember.isPresent()) { //있으면 member 객체 반환
            Member member = findMember.get();
            return new MemberDto().toDto(member);
        } else {
            return null;
        }
    }
}
