package com.doit.study.member.controller;

import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.LoginDto;
import com.doit.study.member.dto.NaverDto;
import com.doit.study.member.naver.NaverLoginBO;
import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NaverLoginController {
    /* NaverLoginBO */
    private final NaverLoginBO naverLoginBO;
    private String apiResult = null;

    //네이버 로그인 성공시 callback호출 메소드
    @RequestMapping(value = "/members/callback", method = { RequestMethod.GET, RequestMethod.POST })
    public String callback(Model model,
                           @RequestParam String code,
                           @RequestParam String state,
                           HttpSession session) throws IOException, ParseException {
        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
        log.info("토큰"+oauthToken);
        //1. 로그인 사용자 정보를 읽어온다.
        apiResult = naverLoginBO.getUserProfile(oauthToken); //String형식의 json데이터
        /** apiResult json 구조
        {"resultcode":"00",
         "message":"success",
         "response":{"id":"33666449","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
         **/
        //2. String형식인 apiResult를 json형태로 바꿈
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(apiResult);
        JSONObject jsonObj = (JSONObject) obj;
        log.info("jsonObj = "+jsonObj);
        //3. 데이터 파싱
        //Top레벨 단계 _response 파싱
        JSONObject response_obj = (JSONObject)jsonObj.get("response");
        log.info("response_obj = "+response_obj);

        //response의 값 파싱
        String email = (String)response_obj.get("email");
        String gender = (String)response_obj.get("gender");
        String name = (String)response_obj.get("name");
        log.info("email={}, gender={}, name={} ",email, gender, name);

        NaverDto naverDto = new NaverDto(name, email, gender);
        log.info("naverDto = "+naverDto);

        //4.파싱 닉네임 세션으로 저장
        session.setAttribute(SessionConst.NAVER_MEMBER, naverDto); //세션 생성
        model.addAttribute("result", apiResult);
        return "redirect:/";
    }

    //로그아웃
    @RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
    public String logout(HttpSession session) throws IOException {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("deleteNaver")
    public String delete() {
        return "redirect:/https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=RmYsFcVGsS8vYkjw-8VDc7aQWPkrWxGsZhBueNxnEFk&client_secret=GwjoZZh06n&access_token=AAAAPHBLfvRic6W7of6tQlAkdJYFf-AT-jmK94F2DCcPdrDe-GgT8e_cjE34eWtidu-NNeKak2O4U7MDiXPYljUtBgY&service_provider=NAVER\n";

    }

}
