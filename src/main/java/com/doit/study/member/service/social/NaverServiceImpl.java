package com.doit.study.member.service.social;

import com.doit.study.mapper.MemberMapper;
import com.doit.study.member.domain.Member;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.naver.NaverLoginApi;
import com.doit.study.member.service.SocialService;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:login.properties")
@PropertySource("classpath:naver_secret.properties")
public class NaverServiceImpl implements SocialService {

    private final MemberMapper memberMapper;

    @Value("${naver.login.client.id}")
    private String CLIENT_ID;

    @Value("${naver.login.client.secret}")
    private String CLIENT_SECRET;

    @Value("${naver.login.redirect.url}")
    private String REDIRECT_URI;

    @Value("${naver.login.session.state}")
    private String SESSION_STATE;

    // 프로필 조회 API URL
    @Value("${naver.login.profile.api.url}")
    private String PROFILE_API_URL;

    /**
     * session에 데이터 저장
     * @param session
     * @param state
     */
    private void setSession(HttpSession session, String state){
        session.setAttribute(SESSION_STATE, state);
    }

    /**
     * 세션에서 데이터 가져오기
     * @param session
     * @return String
     */
    private String getSession(HttpSession session){
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
     * 네이버 아이디로 인증 URL 생성
     * @param session
     * @return String
     */
    @Override
    public String getAuthorizationUrl(HttpSession session) {

        // 세션 유효성 검증을 위하여 난수를 생성
        String state = generateState();
        
        // 생성한 난수 값을 session에 저장
        setSession(session,state);

        // Scribe에서 제공하는 인증 URL 생성 기능을 이용하여 네아로 인증 URL 생성
        OAuth20Service oauthService = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)
                .state(state) //앞서 생성한 난수값을 인증 URL생성시 사용함
                .build(NaverLoginApi.instance());

        //인증 URL 리턴
        return oauthService.getAuthorizationUrl();
    }

    /**
     * 네이버 아이디로 Callback 처리 및 AccessToken 획득
     * @param session
     * @param code
     * @param state
     * @return OAuth2AccessToken
     * @throws IOException
     */
    @Override
    public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {

        // Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인
        String sessionState = getSession(session);
        if(StringUtils.pathEquals(sessionState, state)){

            OAuth20Service oauthService = new ServiceBuilder()
                    .apiKey(CLIENT_ID)
                    .apiSecret(CLIENT_SECRET)
                    .callback(REDIRECT_URI)
                    .state(state)
                    .build(NaverLoginApi.instance());

            // Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득
            OAuth2AccessToken accessToken = oauthService.getAccessToken(code);

            return accessToken;
        }
        return null;
    }

    /**
     * Access Token을 이용하여 네이버 사용자 프로필 API를 호출
     * @param oauthToken
     * @return String
     * @throws IOException
     */
    @Override
    public String getUserProfile(OAuth2AccessToken oauthToken) throws IOException{

        //OAuth20Service 객체 정보 설정
        OAuth20Service oauthService = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)
                .build(NaverLoginApi.instance());

        //OAuthRequest 응답 객체 생성
        OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
        oauthService.signRequest(oauthToken, request);

        //정보 전송
        Response response = request.send();
        return response.getBody();
    }

    /**
     * 네이버 로그인 회원 정보 가져오기
     * @param apiResult
     * @return HashMap<String, String>
     * @throws ParseException
     */
    @Override
    public HashMap<String, String> getSocialUserInfo(String apiResult) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(apiResult);
        JSONObject jsonObj = (JSONObject) obj;
        JSONObject response_obj = (JSONObject)jsonObj.get("response");

        //회원정보 가져오기
        String id = (String)response_obj.get("id");
        String email = (String)response_obj.get("email");
        String gender = (String)response_obj.get("gender");
        String name = (String)response_obj.get("name");

        HashMap<String, String> userInfo = new HashMap<>();

        //회원정보 저장
        userInfo.put("id", id);
        userInfo.put("name", name);
        userInfo.put("email", email);
        userInfo.put("gender", gender);

        return userInfo;
    }

    /***
     * 네이버 회원 정보 삭제
     * @param accessToken
     */
    @Override
    public void deleteAccessToken(String accessToken) {
        String deleteUrl =
                "https://nid.naver.com/oauth2.0/token?grant_type=delete"
                        +   "&client_id=" + CLIENT_ID
                        +   "&client_secret=" + CLIENT_SECRET
                        +   "&access_token=" + accessToken
                        +   "&service_provider=NAVER";
        //result : success
    }

    /**
     * 네이버 로그인한 회원 정보 찾기
     * @param id
     * @return MemberDto
     */
    @Override
    public MemberDto findSocialMember(String id) {
        Optional<Member> findMember = memberMapper.findSocialMemberBySocialId(id);

        if(findMember.isPresent()) {
            Member member = findMember.get();
            return new MemberDto().toDto(member);
        }
        return null;
    }
}
