package com.doit.study.member.controller;

import com.doit.study.member.domain.category.FirstInterestCategory;
import com.doit.study.member.domain.Gender;
import com.doit.study.member.domain.category.SecondInterestCategory;
import com.doit.study.member.domain.category.ThirdInterestCategory;
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
@RequestMapping("/members")
@Controller
@Slf4j
public class JoinController {

    private final MemberService memberService;

    @ModelAttribute("first_categories")
    public List<FirstInterestCategory> firstCategory() {
        List<FirstInterestCategory> categories = new ArrayList<>();
        categories.add(new FirstInterestCategory("back", "백엔드"));
        categories.add(new FirstInterestCategory("front", "프론트엔드"));
        return categories;
    }

    @ModelAttribute("second_categories")
    public List<SecondInterestCategory> secondCategory() {
        List<SecondInterestCategory> categories = new ArrayList<>();
        categories.add(new SecondInterestCategory("study", "스터디"));
        categories.add(new SecondInterestCategory("project", "프로젝트"));
        categories.add(new SecondInterestCategory("cert", "자격증"));
        return categories;
    }

    @ModelAttribute("third_categories")
    public List<ThirdInterestCategory> thirdCategory() {
        List<ThirdInterestCategory> categories = new ArrayList<>();
        categories.add(new ThirdInterestCategory("java", "자바"));
        categories.add(new ThirdInterestCategory("spring", "스프링"));
        return categories;
    }

    @ModelAttribute("gender")
    public List<Gender> gender() {
        List<Gender> genders = new ArrayList<>();
        genders.add(new Gender("M", "남자"));
        genders.add(new Gender("F", "여자"));
        return genders;
    }

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("memberDto") MemberDto memberDto) {
        return "members/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("memberDto") MemberDto memberDto, BindingResult bindingResult) {

        //저장값 출력
        log.info("nickname={}, email={}, password={}, interest1={}, interest2={}, interest3={}",
                memberDto.getNickname(), memberDto.getEmail(), memberDto.getPassword(),
                memberDto.getInterest1(), memberDto.getInterest2(), memberDto.getInterest3());

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "members/joinForm";
        }
        
        memberService.join(memberDto);
        return "redirect:/";
    }

    @PostMapping("nicknameCheck")
    @ResponseBody
    public int nicknameCheck(@RequestParam("nickname") String nickname) {
        log.info("nickname은 ? "+ nickname);
        int result;
        String pattern = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$";
        if(!Pattern.matches(pattern, nickname)){
            return result = 2;
        } else if(nickname.length() > 10 || nickname.length() < 2) {
            return result = 3;
        } else {
            result = memberService.findNickname(nickname);
            return result;
        }
    }

    @PostMapping("emailCheck")
    @ResponseBody
    public int emailCheck(@RequestParam("email") String email) {
        log.info("email은 ? "+ email);
        int result;
        result = memberService.findEmail(email);
        return result;

    }

}
