package com.doit.study.member.controller;

import com.doit.study.member.SessionConst;
import com.doit.study.member.domain.Gender;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.SocialDto;
import com.doit.study.member.service.KakaoService;
import com.doit.study.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;
    private final MemberService memberService;
    private String accessToken;

    //성별 정보
    @ModelAttribute("gender")
    public List<Gender> gender() {
        List<Gender> genders = new ArrayList<>();
        genders.add(new Gender("M", "남자"));
        genders.add(new Gender("F", "여자"));
        return genders;
    }

    /**
     * 카카오 로그인 콜백 컨트롤러
     * @param code
     * @param model
     * @param session
     */
    @RequestMapping(value = "/kakao/callback", method = {RequestMethod.GET, RequestMethod.POST})
    public String callback(@RequestParam String code,
                           Model model,
                           HttpSession session) {
        log.info("code = " + code);

        //access token 발급 받기
        accessToken = kakaoService.getAccessKakaoToken(code);
        log.info("생성된 access_Token은 ?" + accessToken);

        //access token으로 얻은 로그인한 유저 정보 얻어오기
        HashMap<String, String> userInfo = kakaoService.getKaKaoUserInfo(accessToken);
        log.info("nickname : " + userInfo.get("nickname"));
        log.info("email : " + userInfo.get("email"));
        log.info("gender : " + userInfo.get("gender"));
        log.info("id : " + userInfo.get("id"));

        //카카오 회원 고유 id값 가져오기
        String id = userInfo.get("id");

        //카카오 회원 정보 가져오기
        JSONObject kakaoInfo = new JSONObject(userInfo);

        MemberDto memberDto;

        //kakaoDto 초기화
        SocialDto socialDto = new SocialDto(accessToken, id, userInfo.get("nickname"), userInfo.get("email"), userInfo.get("gender"));
        log.info("새로운 socialDto = " + socialDto);

        //회원타입 설정
        socialDto.setSocial_type("kakao");

        //찾는 회원이 회원가입되어 있지 않을 경우
        if(kakaoService.findSocialMember(id) == null) {
            log.info("회원가입이 필요합니다.");
            model.addAttribute("socialDto", socialDto);
            model.addAttribute("url", "/join/kakao");
            model.addAttribute("msg", "회원정보가 없습니다. 회원가입이 필요합니다.");
            return "/alertSocialJoin";
        } else {
            //찾는 회원이 회원가입되어 있을 경우
            log.info("회원 찾기 성공");
            memberDto = kakaoService.findSocialMember(id);
            log.info("회원 socialDto는 ? = " + socialDto);
        }

        //세션에 카카오 회원 정보 전달
        session.setAttribute(SessionConst.KAKAO_MEMBER, memberDto);
        session.setAttribute("token", accessToken);
        log.info("memberDto = " + memberDto);

        String redirectURL = (String) session.getAttribute("redirectURL");
        log.info("redirectUrl= " + redirectURL);

        return "redirect:"+redirectURL;
    }

//    /**
//     * 카카오 로그아웃 컨트롤러
//     * @param request
//     */
//    @RequestMapping(value = "/kakaoLogout")
//    public String logout(HttpServletRequest request) {
//
//        //세션 값 가져오기
//        HttpSession session = request.getSession(false);
//
//        if(session != null) {
//            session.invalidate();
//        }
//
//        return "redirect:/";
//    }


    /**
     * 카카오 연결 끊기
     * @param kakaoDto
     */
    @RequestMapping(value = "/kakaoUnlink")
    public String unlink(@SessionAttribute(name = SessionConst.KAKAO_MEMBER, required = false) SocialDto kakaoDto) {
        log.info("kakaoDto = " + kakaoDto);
        kakaoService.unlinkKakao(accessToken);
        return "/";
    }

    /**
     * 카카오 회원가입 컨트롤러
     * @param kakaoDto
     */
    @GetMapping("/join/kakao")
    public String kakaoJoinForm(@ModelAttribute("kakaoDto") SocialDto kakaoDto) {
        log.info("kakaoDto = " + kakaoDto);
        return "members/kakaoJoinForm";
    }

    /**
     * 카카오 회원 가입 인증 컨트롤러
     * @param kakaoDto
     * @param bindingResult
     * @param session
     */
    @PostMapping("/join/kakao")
    public String kakaoJoin(@Valid @ModelAttribute("kakaoDto") SocialDto kakaoDto,
                            BindingResult bindingResult,
                            HttpSession session) {

        //저장값 출력
        log.info("id={}, name={}, email={}, gender={}",
                kakaoDto.getSocialId(), kakaoDto.getSocialName(), kakaoDto.getSocialEmail(), kakaoDto.getSocialGender());

        //오류 발생시 오류 결과 리턴
        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "members/kakaoJoinForm";
        }

        //오류가 없으면 회원가입 실행
        kakaoDto = memberService.joinSocial(kakaoDto);

        session.setAttribute(SessionConst.KAKAO_MEMBER, kakaoDto);
        return "redirect:/";
    }
}
