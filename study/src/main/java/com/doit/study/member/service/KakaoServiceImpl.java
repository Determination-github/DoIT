package com.doit.study.member.service;

import com.doit.study.mapper.MemberMapper;
import com.doit.study.member.domain.Social;
import com.doit.study.member.dto.KakaoDto;
import com.doit.study.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:login.properties")
public class KakaoServiceImpl implements KakaoService {

    private final MemberMapper memberMapper;

    @Value("${kakao.login.client.id}")
    private String CLIENT_ID; //회원아이디

    @Value("${kakao.login.redirect.url}")
    private String REDIRECT_URL; //리다이렉트 url

    //카카오 로그인 콜백 url
    @Override
    public String getKaKaoCallbackUrl() {
        String kakaoUrl =
                "https://kauth.kakao.com/oauth/authorize"
                        +   "?client_id=" + CLIENT_ID
                        +   "&redirect_uri=" + REDIRECT_URL
                        +   "&response_type=code";
        return kakaoUrl;
    }

    //카카오 로그인 AccessToken 얻기
    @Override
    public String getAccessKakaoToken(String authorize_code) {
        String access_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+CLIENT_ID);  //본인이 발급받은 key
            sb.append("&redirect_uri="+REDIRECT_URL);     // 본인이 설정해 놓은 경로
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("response Code = " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            log.info("response body = " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(result);
            JSONObject jsonObj = (JSONObject) obj;

            access_Token = (String) jsonObj.get("access_token");

            log.info("access_token = " + access_Token);

            br.close();
            bw.close();
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return access_Token;
    }

    
    //access token과 url을 통해 카카오 로그인한 회원 정보 얻기
    @Override
    public HashMap<String, String> getKaKaoUserInfo(String access_Token) {
        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, String> userInfo = new HashMap<String, String>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            log.info("response code = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("response body = " + result);

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(result);
            JSONObject jsonObj = (JSONObject) obj;

            //고유 카카오 회원 아이디
            Long id = (Long) jsonObj.get("id");

            JSONObject properties_obj = (JSONObject)jsonObj.get("properties");
            JSONObject kakao_account_obj = (JSONObject)jsonObj.get("kakao_account");

            String nickname = (String)properties_obj.get("nickname");
            String email = (String)kakao_account_obj.get("email");
            String gender = (String)kakao_account_obj.get("gender");

            userInfo.put("accessToken", access_Token);
            userInfo.put("id", String.valueOf(id));
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);
            userInfo.put("gender", gender);

        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userInfo;
    }

    //카카오 회원 정보 삭제
    @Override
    public void unlinkKakao(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";
        try { URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println(result);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public MemberDto kakaoToMember(KakaoDto kakaoDto) {

        MemberDto memberDto = new MemberDto(
                kakaoDto.getKakaoEmail(),
                kakaoDto.getKakaoNickname(),
                kakaoDto.getKakaoName(),
                kakaoDto.getKakaoGender(),
                kakaoDto.getKakaoInterest1(),
                kakaoDto.getKakaoInterest2(),
                kakaoDto.getKakaoInterest3()
                );

        return memberDto;
    }

    //카카오 회원 회원가입
    @Override
    public KakaoDto joinSocial(KakaoDto kakaoDto) {
        Social social = kakaoDto.toEntity(kakaoDto);

        //소셜회원 객체 저장값 출력
        log.info("user_id={}, name={}, email={}, sex={}," +
                        "interest1={}, interest2={}, interest3={}, nickname={}",
                social.getUser_id(), social.getName(), social.getEmail(),
                social.getSex(), social.getInterest1(), social.getInterest2(),
                social.getInterest3(), social.getNickname());

        Integer result = memberMapper.insertSocial(social);

        if(result != null) {
            return new KakaoDto().toDto(social);
        }
        return null;
    }

    //카카오 로그인한 회원 정보 찾기
    @Override
    public KakaoDto findSocialMember(String id) {

        Optional<Social> findMember = memberMapper.findSocialMemberById(id);
        log.info("findMember는 findMember={}", findMember);

        if(findMember.isPresent()) {
            Social social = findMember.get();
            return new KakaoDto().toDto(social);
        }

        return null;
    }
}
