package com.doit.study.member.service;

import com.doit.study.member.domain.Social;
import com.doit.study.member.dto.*;
import com.doit.study.mapper.MemberMapper;
import com.doit.study.member.domain.Member;
import com.doit.study.profile.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final MemberMapper memberMapper;

    /**
     * 일반 회원 가입
     * @param memberDto
     * @return MemberDto
     * @throws Exception
     */
    @Override
    public MemberDto join(MemberDto memberDto) throws Exception {

        //password 암호화
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        //dto to entity
        Member member = memberDto.toEntity(memberDto);

        //회원 정보 저장
        Integer result = memberMapper.insert(member);

        if(result != null) {
            return new MemberDto().toDto(member);
        } else {
            return null;
        }
    }

    /**
     * 소셜 회원가입
     * @param socialDto
     * @return SocialDto
     * @throws Exception
     */
    @Override
    public SocialDto joinSocial(SocialDto socialDto) throws Exception {
        Member member = socialDto.toEntity(socialDto);

        Integer result = memberMapper.insertSocialToUser(member);

        if(result != null) {
            //가입 정보 가져오기
            Integer user_id = memberMapper.findLastId();

            socialDto.setUser_id(user_id);

            //dto to entity
            Social social = socialDto.toSocial(socialDto.getUser_id(), socialDto.getSocialId(),
                                socialDto.getSocial_type(), socialDto.getToken());

            log.info("socialDto={}", socialDto);

            //social 회원 정보 저장
            Integer socialResult = memberMapper.insertSocial(social);
            if(social != null) {
                return socialDto;
            }
        }
        return null;
    }

    /**
     * 로그인
     * @param loginDto
     * @return MemberDto
     */
    @Override
    public MemberDto login(LoginDto loginDto) {
        //이메일과 비밀번호 정보 가져오기
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        //member 객체 찾기 by email
        Optional<Member> findMember = memberMapper.findByEmail(email);
        if(!findMember.isPresent()) { //정보가 없으면 null 반환
            return null;
        }

        Member member = findMember.get();
        if(passwordEncoder.matches(password, member.getPassword())) { //패스워드 일치하는지 확인
            return new MemberDto().toDto(member);
        } else {
            return null;
        }
    }

    /**
     * 닉네임 중복 확인
     * @param nickname
     * @return int
     */
    @Override
    public int findNickname(String nickname) {
        return memberMapper.checkNickname(nickname);
    }

    /**
     * 이메일 중복 확인
     * @param email
     * @return int
     */
    @Override
    public int findEmail(String email) {
        return memberMapper.checkEmail(email);
    }

    /**
     * 회원 정보 가져오기
     * @param id
     * @return ProfileDto
     */
    @Override
    public ProfileDto findMember(Integer id) {
        //프로필 정보가져오기
        Member member = memberMapper.findMember(id);
        ProfileDto profileDto = new ProfileDto(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getPassword()
        );

        return profileDto;
    }
}
