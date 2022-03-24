package com.doit.study.member.service;

import com.doit.study.member.dto.*;
import com.doit.study.mapper.MemberMapper;
import com.doit.study.member.domain.Member;
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
        String user_id = UUID.randomUUID().toString();
        Member member = memberDto.toEntity(user_id, memberDto);
        
        //member 객체 저장값 출력
        log.info("user_id={}, name={}, email={}, password={}, sex={}," +
                        "interest1={}, interest2={}, interest3={}, nickname={}",
                member.getUser_id(), member.getName(), member.getEmail(), member.getPassword(),
                member.getSex(), member.getInterest1(), member.getInterest2(),
                member.getInterest3(), member.getNickname());

        Integer result = memberMapper.insert(member);

        if(result != null) {
            return new MemberDto().toDto(member);
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


}
