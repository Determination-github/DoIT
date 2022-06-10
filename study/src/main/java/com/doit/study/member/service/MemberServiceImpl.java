package com.doit.study.member.service;

import com.doit.study.member.domain.Social;
import com.doit.study.member.dto.*;
import com.doit.study.mapper.MemberMapper;
import com.doit.study.member.domain.Member;
import com.doit.study.profile.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberMapper memberMapper;

    @Override
    public MemberDto join(MemberDto memberDto) {
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

    @Override
    public SocialDto joinSocial(SocialDto socialDto) {
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

        if(member.getPassword().equals(password)) {
            log.info("비밀번호가 일치해야 실행됨");
            return new MemberDto().toDto(member);
        }
        return null;
    }

    @Override
    public int findNickname(String nickname) {
        return memberMapper.checkNickname(nickname);
    }

    @Override
    public int findEmail(String email) {
        return memberMapper.checkEmail(email);
    }

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
