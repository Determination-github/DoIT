package com.doit.study.member.controller;

import com.doit.study.member.SessionConst;
import com.doit.study.member.domain.Gender;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.SocialDto;
import com.doit.study.member.service.MemberService;
import com.doit.study.member.service.SocialService;
import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    @Autowired
    @Qualifier("kakaoServiceImpl")
    private SocialService kakaoService;

    private final MemberService memberService;
    private String accessToken;

    /**
     * 성별 정보 담기
     * @return
     */
    @ModelAttribute("gender")
    public List<Gender> gender() {
        List<Gender> genders = new ArrayList<>();
        genders.add(new Gender("M", "남자"));
        genders.add(new Gender("F", "여자"));
        return genders;
    }

    /**
     * 카카오 로그인 성공시 callback호출 메서드
     * @param model
     * @param code
     * @param state
     * @param session
     * @return String
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value = "/kakao/callback", method = {RequestMethod.GET, RequestMethod.POST})
    public String callback(@RequestParam String code,
                           @RequestParam String state,
                           Model model,
                           HttpSession session) throws IOException, ParseException {

        //토큰 발급
        OAuth2AccessToken oauthToken;
        oauthToken = kakaoService.getAccessToken(session, code, state);
        if(oauthToken == null) {
            throw new IOException();
        }

        //accessToken 가져오기
        String accessToken = oauthToken.getAccessToken();

        //access token으로 로그인한 유저 정보 얻어오기
        HashMap<String, String> userInfo = kakaoService.getSocialUserInfo(accessToken);

        //카카오 회원 고유 id값 가져오기
        String id = userInfo.get("id");

        //kakaoDto 초기화
        SocialDto socialDto = new SocialDto(accessToken, id, userInfo.get("nickname"), userInfo.get("email"), userInfo.get("gender"));

        //회원타입 설정
        socialDto.setSocial_type("kakao");

        MemberDto memberDto;

        if(kakaoService.findSocialMember(id) == null) { //찾는 회원이 회원가입되어 있지 않을 경우
            model.addAttribute("socialDto", socialDto);
            model.addAttribute("url", "/join/kakao");
            model.addAttribute("msg", "회원정보가 없습니다. 회원가입이 필요합니다.");
            return "members/alertSocialJoin";
        } else { //이미 회원 가입이 되어 있는 경우
            memberDto = kakaoService.findSocialMember(id);
        }

        //세션에 카카오 회원 정보 전달
        session.setAttribute(SessionConst.KAKAO_MEMBER, memberDto);
        session.setAttribute("token", accessToken);

        //리다이렉트
        String redirectURL = (String) session.getAttribute("redirectURL");

        return "redirect:"+redirectURL;
    }

    /**
     * 카카오 회원가입 컨트롤러
     * @param kakaoDto
     */
    @GetMapping("/join/kakao")
    public String kakaoJoinForm(@ModelAttribute("kakaoDto") SocialDto kakaoDto) {
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
                            HttpSession session) throws Exception {

        //유효성 검사
        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "members/kakaoJoinForm";
        }

        //오류가 없으면 회원가입 실행
        kakaoDto = memberService.joinSocial(kakaoDto);

        //MemberDto로 전환
        MemberDto memberDto = toMemberDto(kakaoDto);

        session.setAttribute(SessionConst.KAKAO_MEMBER, memberDto);
        return "redirect:";
    }

    /**
     * MemberDto로 전환
     * @param kakaoDto
     */
    private MemberDto toMemberDto(SocialDto kakaoDto) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(kakaoDto.getUser_id());
        memberDto.setNickname(kakaoDto.getSocialNickname());
        return memberDto;
    }
}
