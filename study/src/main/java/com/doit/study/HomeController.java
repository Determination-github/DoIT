package com.doit.study;

import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.LoginDto;
import com.doit.study.member.dto.NaverDto;
import com.doit.study.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping
//    @ResponseBody
    public String home(
            @SessionAttribute(name = SessionConst.NAVER_MEMBER, required = false) NaverDto naverDto,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginDto loginDto,
            Model model) {

        //네이버로 로그인 했는데 회원정보가 없는 경우
        if(naverDto != null) {
            String naverEmail = naverDto.getNaverEmail();
            if (memberService.findNaverMember(naverEmail) == null) {
                log.info("회원가입이 필요합니다.");
                model.addAttribute("naverMember", naverDto);
                return "redirect:/join";
            }
        }

        if(loginDto == null && naverDto == null) {
            log.info("회원정보가 없습니다.");
            return "home";
        }

        //loginDto의 값이 있는 경우(로그인 한 경우)
        model.addAttribute("member", loginDto);
        log.info("로그인 성공");
        return "index";
    }
}
