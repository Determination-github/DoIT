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
@RequestMapping("/join")
@Controller
@Slf4j
public class JoinController {

    private final MemberService memberService;
    private String pattern;

    //첫 번째 카테고리 정보
    @ModelAttribute("first_categories")
    public List<FirstInterestCategory> firstCategory() {
        List<FirstInterestCategory> categories = new ArrayList<>();
        categories.add(new FirstInterestCategory("back", "백엔드"));
        categories.add(new FirstInterestCategory("front", "프론트엔드"));
        return categories;
    }

    //두 번째 카테고리 정보
    @ModelAttribute("second_categories")
    public List<SecondInterestCategory> secondCategory() {
        List<SecondInterestCategory> categories = new ArrayList<>();
        categories.add(new SecondInterestCategory("study", "스터디"));
        categories.add(new SecondInterestCategory("project", "프로젝트"));
        categories.add(new SecondInterestCategory("cert", "자격증"));
        return categories;
    }

    //세 번째 카테고리 정보
    @ModelAttribute("third_categories")
    public List<ThirdInterestCategory> thirdCategory() {
        List<ThirdInterestCategory> categories = new ArrayList<>();
        categories.add(new ThirdInterestCategory("java", "자바"));
        categories.add(new ThirdInterestCategory("spring", "스프링"));
        return categories;
    }

    //성별 정보
    @ModelAttribute("gender")
    public List<Gender> gender() {
        List<Gender> genders = new ArrayList<>();
        genders.add(new Gender("male", "남자"));
        genders.add(new Gender("female", "여자"));
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

        return "redirect:/login";
    }

    /**
     * 이름 패턴 검사 컨트롤러
     * @param name
     */
    @PostMapping("nameCheck")
    @ResponseBody
    public int nameCheck(@RequestParam("name") String name) {
        log.info("name은 ? "+ name);

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
        log.info("nickname은 ? "+ nickname);
        int result;

        //검증 패턴
        pattern = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$";
        if(!Pattern.matches(pattern, nickname)){
            return result = 1;
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
        log.info("email은 ? "+ email);
        int result;
        
        //검증 패턴
        pattern = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";
        if(!Pattern.matches(pattern, email)){
            return result = 2;
        }

        //이메일 중복 검사
        return memberService.findEmail(email);
    }
}
