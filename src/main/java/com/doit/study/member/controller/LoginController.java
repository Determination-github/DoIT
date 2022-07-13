package com.doit.study.member.controller;

import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.LoginDto;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.service.MemberService;
import com.doit.study.member.service.SocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@Slf4j
public class LoginController {

    private final MemberService memberService;

    @Autowired
    @Qualifier("naverServiceImpl")
    private SocialService naverService;

    @Autowired
    @Qualifier("kakaoServiceImpl")
    private SocialService kakaoService;

    /**
     * 네이버/카카오 콜백 url 생성 및 로그인 컨트롤러
     * @param loginDto
     * @param model
     * @param session
     */
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto,
                            Model model,
                            HttpSession session,
                            @RequestParam(defaultValue = "/") String redirectURL) {

        //네이보 로그인을 위한 콜백 url 생성
        String naverAuthUrl = naverService.getAuthorizationUrl(session);

//        //카카오 콜백 url 생성
        String kakaoAuthUrl = kakaoService.getAuthorizationUrl(session);

        //네이버, 카카오 콜백 url 저장
        model.addAttribute("naverUrl", naverAuthUrl);
        model.addAttribute("kakaoUrl", kakaoAuthUrl);

        session.setAttribute("redirectURL", redirectURL);

        return "members/loginForm";
    }

    /**
     * 일반 로그인 오류 검증 컨트롤러
     * @param loginDto
     * @param bindingResult
     * @param redirectURL
     * @param request
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        //유효성 검사
        if(bindingResult.hasErrors()) {
            return "/members/loginForm";
        }

        //입력한 이메일과 비밀번호로 회원 정보 가져오기
        MemberDto memberDto = memberService.login(loginDto);

        //로그인 실패 시
        if(memberDto == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "members/loginForm";
        }

        //로그인 성공 시
        //세션에 회원 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, memberDto);
        return "redirect:" + redirectURL;
    }

    /**
     * 로그아웃 컨트롤러
     * @param request
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) { //세션 초기화
            session.invalidate();
        }
        return "redirect:/";
    }

    /**
     * 비밀번호 찾기
     * @return String
     */
    @GetMapping("/find")
    public String find() {
        return "members/findPassword";
    }

}