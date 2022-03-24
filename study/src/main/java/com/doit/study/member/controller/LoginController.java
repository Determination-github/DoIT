package com.doit.study.member.controller;

import com.doit.study.member.dto.LoginDto;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.naver.NaverLoginBO;
import com.doit.study.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
@Slf4j
public class LoginController {

    private final MemberService memberService;
    private final NaverLoginBO naverLoginBO;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto, Model model, HttpSession session) {
        /* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
        //https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
        //redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
        log.info("네이버: " + naverAuthUrl);

        //네이버
        model.addAttribute("url", naverAuthUrl);

        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        //유효성 검사
        if(bindingResult.hasErrors()) {
            log.info("타입 오류 발생, error={}", bindingResult);
            return "members/loginForm";
        }

        log.info("login값 email={}, password={}", loginDto.getEmail(), loginDto.getPassword());

        MemberDto memberDto = memberService.login(loginDto);
        log.info("memberDto={}", memberDto);

        //로그인 실패 시
        if(memberDto == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "members/loginForm";
        }

        //로그인 성공 시
        //세션에 회원 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("LoginDto", loginDto);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
