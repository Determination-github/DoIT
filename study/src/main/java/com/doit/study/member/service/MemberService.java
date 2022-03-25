package com.doit.study.member.service;

import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.LoginDto;
import com.doit.study.member.dto.NaverDto;

public interface MemberService {

    //회원가입
    MemberDto join(MemberDto memberDto);

    //로그인
    MemberDto login(LoginDto loginDto);

    //닉네임 중복 확인
    int findNickname(String nickname);

    //이메일 중복 확인
    int findEmail(String email);

    //네이버 로그인 회원의 회원가입 여부 확인
    MemberDto findNaverMember(String email);

}
