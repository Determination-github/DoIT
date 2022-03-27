package com.doit.study;

import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.KakaoDto;
import com.doit.study.member.dto.LoginDto;
import com.doit.study.member.dto.NaverDto;
import com.doit.study.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            @SessionAttribute(name = SessionConst.KAKAO_MEMBER, required = false) KakaoDto kakaoDto,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginDto loginDto,
            Model model) {

        //네이버로 로그인 했는데 회원정보가 없는 경우
        if(naverDto != null) {
            String naverEmail = naverDto.getNaverEmail();
            if (memberService.findSocialMember(naverEmail) == null) {
                log.info("회원가입이 필요합니다.");
                model.addAttribute("naverMember", naverDto);
                return "redirect:/members/join";
            }
        }

        if(kakaoDto != null) {
            String kakaoEmail = kakaoDto.getKakaoEmail();
            if(kakaoEmail == null) {
                log.info("회원가입이 필요합니다. - 이메일 정보가 없음");
                model.addAttribute("kakaoMember", kakaoDto);
                return "redirect:/members/join";
            }

            if(kakaoEmail != null) {
                if(memberService.findSocialMember(kakaoEmail) == null) {
                    log.info("회원가입이 필요합니다.");
                    model.addAttribute("kakaoMember", kakaoDto);
                    return "redirect:/members/join";
                }
            }
        }

        if(loginDto == null && naverDto == null && kakaoDto == null) {
            log.info("회원정보가 없습니다.");
            return "home";
        }

        //loginDto의 값이 있는 경우(로그인 한 경우)
        model.addAttribute("member", loginDto);
        log.info("로그인 성공");
        return "index";
    }
}
