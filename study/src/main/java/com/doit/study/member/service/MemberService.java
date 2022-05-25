package com.doit.study.member.service;

import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.LoginDto;
import com.doit.study.profile.dto.ProfileDto;
import com.doit.study.member.dto.SocialDto;

public interface MemberService {

    //회원가입
    MemberDto join(MemberDto memberDto);

    //로그인
    MemberDto login(LoginDto loginDto);

    //닉네임 중복 확인
    int findNickname(String nickname);

    //이메일 중복 확인
    int findEmail(String email);

    //회원 정보 가져오기
    ProfileDto findMember(Integer id);

    SocialDto joinSocial(SocialDto socialDto);

}
