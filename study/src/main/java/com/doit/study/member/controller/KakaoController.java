package com.doit.study.member.controller;

import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.KakaoDto;
import com.doit.study.member.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;

    @RequestMapping(value = "/kakao/callback", method = { RequestMethod.GET, RequestMethod.POST })
    public String callback(@RequestParam String code, Model model, HttpSession session) {
        log.info("code = " + code);

        String access_Token = kakaoService.getAccessKakaoToken(code);
        log.info("생성된 access_Token은 ?" + access_Token);

        HashMap<String, String> userInfo = kakaoService.getKaKaoUserInfo(access_Token);
        log.info("access_Token : " + userInfo.get("accessToken"));
        log.info("nickname : " + userInfo.get("nickname"));
        log.info("email : " + userInfo.get("email"));
        log.info("gender : " + userInfo.get("gender"));

        JSONObject kakaoInfo =  new JSONObject(userInfo);

        KakaoDto kakaoDto = new KakaoDto(userInfo.get("nickname"), userInfo.get("accessToken"), userInfo.get("email"), userInfo.get("gender"));
        log.info("kakaoDto = " + kakaoDto);

        session.setAttribute(SessionConst.KAKAO_MEMBER, kakaoDto);
        model.addAttribute("kakaoInfo", kakaoInfo);

        return "redirect:/"; //본인 원하는 경로 설정
    }

    @RequestMapping(value="/kakaoLogout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        KakaoDto kakao = (KakaoDto) session.getAttribute(SessionConst.KAKAO_MEMBER);
        log.info("kakao = " + kakao);
        if(!kakao.getKakaoName().isEmpty()) {
            session.invalidate();
        }
        return "redirect:/";
    }


    //카카오 연결끊기
    @RequestMapping(value="/kakaoUnlink")
    public String unlink(@SessionAttribute(name = SessionConst.KAKAO_MEMBER, required = false) KakaoDto kakaoDto) {
        log.info("kakaoDto = " + kakaoDto);
        String accessToken = kakaoDto.getAccessToken();
        kakaoService.unlinkKakao(accessToken);
        return "redirect:/";
    }


}
