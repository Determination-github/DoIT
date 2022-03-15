package com.doit.study.member.service;

import com.doit.study.member.dto.MemberDto;
import com.doit.study.mapper.MemberMapper;
import com.doit.study.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
                        "address={}, interest1={}, interest2={}, interest3={}, nickname={}",
                member.getUser_id(), member.getName(), member.getEmail(), member.getPassword(),
                member.getSex(), member.getAddress(), member.getInterest1(), member.getInterest2(),
                member.getInterest3(), member.getNickname());

        Integer result = memberMapper.insert(member);

        if(result != null) {
            memberDto.toDto(member);
            return memberDto;
        }

        return null;
    }


}
