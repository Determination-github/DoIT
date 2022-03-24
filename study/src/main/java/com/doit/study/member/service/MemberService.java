package com.doit.study.member.service;

import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.LoginDto;

public interface MemberService {

    //회원가입
    MemberDto join(MemberDto memberDto);

    //로그인
    MemberDto login(LoginDto loginDto);

    //닉네임 중복 확인
    int findNickname(String nickname);

    //이메일 중복 확인
    int findEmail(String email);

}
