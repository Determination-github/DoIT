package com.doit.study.member.controller;

import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping
@RestController
@Slf4j
public class MemberController {


    private final MemberService memberService;

    @PostMapping("/join")
    @ResponseBody
    public String join(@ModelAttribute MemberDto memberDto) {
        //저장값 출력
        log.info("nickname={}, email={}, password={}, interest1={}, interest2={}, interest3={}",
                memberDto.getNickname(), memberDto.getEmail(), memberDto.getPassword(),
                memberDto.getInterest1(), memberDto.getInterest2(), memberDto.getInterest3());
        
        memberService.join(memberDto);
        return "ok";
    }
}
