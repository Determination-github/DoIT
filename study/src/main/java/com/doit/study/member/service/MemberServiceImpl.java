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

        Member member = memberDto.toEntity(memberDto);

        //member 객체 저장값 출력
        log.info("name={}, email={}, password={}, gender={}, nickname={}",
                member.getName(), member.getEmail(), member.getPassword(),
                member.getGender(), member.getNickname());

        Integer result = memberMapper.insert(member);

        if(result != null) {
            return new MemberDto().toDto(member);
        }

        return null;
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

        //소셜회원 객체 저장값 출력
        log.info("name={}, email={}, gender={}, nickname={}",
                member.getName(), member.getEmail(), member.getGender(), member.getNickname());

        Integer result = memberMapper.insertSocialToUser(member);

        if(result != null) {
            int id = memberMapper.findLastId();
            Social social = socialDto.toSocial(id, socialDto.getSocialId(),
                                socialDto.getSocial_type(), socialDto.getToken());
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
        String email = loginDto.getEmail();
        log.info("email은 email={}", email);
        String password = loginDto.getPassword();
        log.info("password은 password={}", password);


        Optional<Member> findMember = memberMapper.findByEmail(email);
        log.info("findMember는 findMember={}", findMember);
        if(!findMember.isPresent()) {
            return null;
        }

        Member member = findMember.get();
        log.info("member={}", member);
        log.info("password={}", member.getPassword());

        if(passwordEncoder.matches(password, member.getPassword())) {
            log.info("비밀번호가 일치해야 실행됨");
            return new MemberDto().toDto(member);
        }
        return null;
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
        Member member = memberMapper.findMember(id);
        ProfileDto profileDto = new ProfileDto(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getPassword()
        );

        log.info("profileDto={}", profileDto);

        return profileDto;
    }
}
