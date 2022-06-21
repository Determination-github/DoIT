package com.doit.study.member.controller;

import com.doit.study.member.domain.Gender;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@RequestMapping("/join")
@Controller
@Slf4j
public class JoinController {

    private final MemberService memberService;
    private String pattern;

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
     * 회원가입 화면 불러오기
     * @param memberDto
     * @param social
     */
    @GetMapping
    public String joinForm(@ModelAttribute("memberDto") MemberDto memberDto,
                           @RequestParam(required = false, defaultValue = "basic") String social) {
        return "members/joinForm";
    }

    /**
     * 일반 회원가입 오류 인증 컨트롤러
     * @param memberDto
     * @param bindingResult
     */
    @PostMapping
    public String join(@Valid @ModelAttribute("memberDto") MemberDto memberDto, BindingResult bindingResult) throws Exception {
        //유효성 검사
        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "members/joinForm";
        }

        //회원가입
        memberService.join(memberDto);

        return "redirect:login";
    }

    //-----------------------------------유효성 검사 메서드 ---------------------------------


    /**
     * 이름 패턴 검사 컨트롤러
     * @param name
     */
    @PostMapping("nameCheck")
    @ResponseBody
    public int nameCheck(@RequestParam("name") String name) {
        //결과 초기값
        int result = 0;

        //검증 패턴
        pattern = "^[가-힣]+$";
        if(!Pattern.matches(pattern, name)){
            return result = 1;
        }
        return result;
    }

    /**
     * 닉네임 패턴 및 중복 체크 컨트롤러
     * @param nickname
     */
    @PostMapping("nicknameCheck")
    @ResponseBody
    public int nicknameCheck(@RequestParam("nickname") String nickname) {
        int result = 0;

        //검증 패턴
        pattern = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$";
        if(!Pattern.matches(pattern, nickname)){
            return result = 3;
        } else if(nickname.length() > 10 || nickname.length() < 2) {
            return result = 2;
        } else {
            //닉네임 중복 검사
            result = memberService.findNickname(nickname);
            return result;
        }
    }

    /**
     * 이메일 패턴 및 중복 검사 컨트롤러
     * @param email
     */
    @PostMapping("emailCheck")
    @ResponseBody
    public int emailCheck(@RequestParam("email") String email) {
        int result;
        
        //검증 패턴
        pattern = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";
        if(!Pattern.matches(pattern, email)){
            return result = 2;
        }

        //이메일 중복 검사
        return memberService.findEmail(email);
    }

    /**
     * 비밀번호 체크
     * @param password
     * @return
     */
    @PostMapping("passwordCheck")
    @ResponseBody
    public int passwordCheck(@RequestParam("password") String password) {
        int result = 0;

        //검증 패턴
        pattern = "^(?=.*[a-z])(?=.*[0-9])(?=.*\\W).{8,16}";
        if(!Pattern.matches(pattern, password)){
            return result = 1;
        }

        return result;
    }
}
