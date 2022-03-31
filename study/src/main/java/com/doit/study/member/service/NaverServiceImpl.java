package com.doit.study.member.service;

import com.doit.study.mapper.MemberMapper;
import com.doit.study.member.domain.Social;
import com.doit.study.member.dto.KakaoDto;
import com.doit.study.member.dto.NaverDto;
import com.doit.study.member.naver.NaverLoginApi;
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
public class NaverServiceImpl implements NaverService {

    private final MemberMapper memberMapper;

    /* 인증 요청문을 구성하는 파라미터 */
    //client_id: 애플리케이션 등록 후 발급받은 클라이언트 아이디
    //response_type: 인증 과정에 대한 구분값. code로 값이 고정돼 있습니다.
    //redirect_uri: 네이버 로그인 인증의 결과를 전달받을 콜백 URL(URL 인코딩). 애플리케이션을 등록할 때 Callback URL에 설정한 정보입니다.
    //state: 애플리케이션이 생성한 상태 토큰
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

    /* http session에 데이터 저장 */
    private void setSession(HttpSession session, String state){
        session.setAttribute(SESSION_STATE, state);
    }
    
    /* http session에서 데이터 가져오기 */
    private String getSession(HttpSession session){
        return (String) session.getAttribute(SESSION_STATE);
    }

    //state 생성 메서드
    public String generateState()
    {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    /* 네이버 아이디로 인증  URL 생성  Method */
    @Override
    public String getAuthorizationUrl(HttpSession session) {

        /* 세션 유효성 검증을 위하여 난수를 생성 */
        String state = generateState();
        
        /* 생성한 난수 값을 session에 저장 */
        setSession(session,state);

        /* Scribe에서 제공하는 인증 URL 생성 기능을 이용하여 네아로 인증 URL 생성 */
        OAuth20Service oauthService = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)
                .state(state) //앞서 생성한 난수값을 인증 URL생성시 사용함
                .build(NaverLoginApi.instance());

        //인증 URL 리턴
        return oauthService.getAuthorizationUrl();
    }

    /* 네이버아이디로 Callback 처리 및  AccessToken 획득 Method */
    @Override
    public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {

        /* Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인 */
        String sessionState = getSession(session);
        if(StringUtils.pathEquals(sessionState, state)){

            OAuth20Service oauthService = new ServiceBuilder()
                    .apiKey(CLIENT_ID)
                    .apiSecret(CLIENT_SECRET)
                    .callback(REDIRECT_URI)
                    .state(state)
                    .build(NaverLoginApi.instance());

            /* Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득 */
            OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
            return accessToken;
        }
        return null;
    }

    /* Access Token을 이용하여 네이버 사용자 프로필 API를 호출 */
    @Override
    public String getUserProfile(OAuth2AccessToken oauthToken) throws IOException{

        OAuth20Service oauthService =new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI).build(NaverLoginApi.instance());

        OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
        oauthService.signRequest(oauthToken, request);
        Response response = request.send();
        return response.getBody();
    }

    //네이버 로그인 회원 정보 가져오기
    @Override
    public HashMap<String, String> getNaverUserInfo(String apiResult) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(apiResult);
        JSONObject jsonObj = (JSONObject) obj;
        log.info("jsonObj = "+jsonObj);


        JSONObject response_obj = (JSONObject)jsonObj.get("response");
        log.info("response_obj = "+response_obj);

        //회원정보 가져오기
        String id = (String)response_obj.get("id");
        String email = (String)response_obj.get("email");
        String gender = (String)response_obj.get("gender");
        String name = (String)response_obj.get("name");
        log.info("id={}, email={}, gender={}, name={} ", id, email, gender, name);

        HashMap<String, String> userInfo = new HashMap<>();

        //회원정보 저장
        userInfo.put("id", id);
        userInfo.put("name", name);
        userInfo.put("email", email);
        userInfo.put("gender", gender);

        return userInfo;
    }

    @Override
    public NaverDto joinSocial(NaverDto naverDto) {
        Social social = naverDto.toEntity(naverDto);

        //소셜회원 객체 저장값 출력
        log.info("user_id={}, name={}, email={}, sex={}," +
                        "interest1={}, interest2={}, interest3={}, nickname={}",
                social.getUser_id(), social.getName(), social.getEmail(),
                social.getSex(), social.getInterest1(), social.getInterest2(),
                social.getInterest3(), social.getNickname());

        Integer result = memberMapper.insertSocial(social);

        if(result != null) {
            return new NaverDto().toDto(social);
        }
        return null;
    }

    //네이버 로그인한 회원 정보 찾기
    @Override
    public NaverDto findSocialMember(String id) {
        Optional<Social> findMember = memberMapper.findSocialMemberById(id);
        log.info("findMember는 findMember={}", findMember);

        if(findMember.isPresent()) {
            Social social = findMember.get();
            return new NaverDto().toDto(social);
        }

        return null;
    }
}
