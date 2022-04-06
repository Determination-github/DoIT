package com.doit.study.member.controller;

import com.doit.study.member.dto.ProfileDto;
import com.doit.study.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final MemberService memberService;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable String id, Model model) {
        ProfileDto profileDto = new ProfileDto();
        profileDto = memberService.findMember(id);
        log.info("profiledto={}",profileDto);
        model.addAttribute("profileDto", profileDto);
        return "/members/profile";
    }
}
