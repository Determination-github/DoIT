package com.doit.study.member.controller;

import com.doit.study.member.SessionConst;
import com.doit.study.member.domain.Gender;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.SocialDto;
import com.doit.study.member.service.MemberService;
import com.doit.study.member.service.NaverService;
import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
public class NaverController {

    private final NaverService naverService;
    private final MemberService memberService;
    private String apiResult;

    /**
     * 성별 정보
     * @return List<Gender>
     */
    @ModelAttribute("gender")
    public List<Gender> gender() {
        List<Gender> genders = new ArrayList<>();
        genders.add(new Gender("M", "남자"));
        genders.add(new Gender("F", "여자"));
        return genders;
    }

    /**
     * 네이버 로그인 성공시 callback호출 메소드
     * @param model
     * @param code
     * @param state
     * @param session
     * @return String
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value = "/naver/callback", method = { RequestMethod.GET, RequestMethod.POST })
    public String callback(Model model,
                           @RequestParam String code,
                           @RequestParam String state,
                           HttpSession session) throws IOException, ParseException {

        //토큰 발급
        OAuth2AccessToken oauthToken;
        oauthToken = naverService.getAccessToken(session, code, state);

        if(oauthToken == null) {
            throw new IOException();
        }

        //1. 로그인 사용자 정보를 읽어온다.
        apiResult = naverService.getUserProfile(oauthToken); //String형식의 json데이터

        //String형식인 apiResult를 json형태로 바꿈
        HashMap<String, String> userInfo = naverService.getNaverUserInfo(apiResult);

        //accessToken 가져오기
        String accessToken = oauthToken.getAccessToken();

        //socialDto 객체 생성
        SocialDto socialDto =
                new SocialDto(accessToken, userInfo.get("id"), userInfo.get("name"), userInfo.get("email"), userInfo.get("gender"));

        //회원 타입 설정
        socialDto.setSocial_type("naver");

        //네이버 회원 id 가져오기
        String social_id = userInfo.get("id");

        MemberDto memberDto;

        if(naverService.findSocialMember(social_id) == null) { //찾는 회원이 회원가입되어 있지 않을 경우
            model.addAttribute("socialDto", socialDto);
            model.addAttribute("url", "/join/naver");
            model.addAttribute("msg", "회원정보가 없습니다. 회원가입이 필요합니다.");
            return "members/alertSocialJoin";
        } else { //찾는 회원이 회원가입되어 있을 경우
            memberDto = naverService.findSocialMember(social_id);
        }

        //세션에 네이버 회원 정보 전달
        session.setAttribute(SessionConst.NAVER_MEMBER, memberDto);
        session.setAttribute("token", accessToken);

        String redirectURL = (String) session.getAttribute("redirectURL");

        return "redirect:"+redirectURL;
    }

    /**
     * 네이버 회원가입 컨트롤러
     * @param naverDto
     * @return
     */
    @GetMapping("/join/naver")
    public String naverJoinForm(@ModelAttribute("naverDto") SocialDto naverDto) {
        return "members/naverJoinForm";
    }

    /**
     * 네이버 회원 가입 인증 컨트롤러
     * @param naverDto
     * @param bindingResult
     * @param session
     */
    @PostMapping("/join/naver")
    @Transactional
    public String naverJoin(@Valid @ModelAttribute("naverDto") SocialDto naverDto,
                            BindingResult bindingResult,
                            HttpSession session) throws Exception {

        //유효성 검사
        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "members/naverJoinForm";
        }

        //social_type 설정
        naverDto.setSocial_type("naver");

        //오류가 없으면 회원가입 실행
        naverDto = memberService.joinSocial(naverDto);

        //MemberDto로 전환
        MemberDto memberDto = toMemberDto(naverDto);

        session.setAttribute(SessionConst.NAVER_MEMBER, memberDto);
        return "redirect:";
    }

    /**
     * MemberDto로 전환
     * @param naverDto
     */
    private MemberDto toMemberDto(SocialDto naverDto) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(naverDto.getUser_id());
        memberDto.setNickname(naverDto.getSocialNickname());
        return memberDto;
    }

}
