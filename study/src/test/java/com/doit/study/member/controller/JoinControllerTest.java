package com.doit.study.member.controller;

import com.doit.study.mapper.MemberMapper;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.service.MemberService;
import com.doit.study.member.service.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

//@Transactional
//@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class JoinControllerTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void join() {
        MemberDto memberDto = new MemberDto();
        MemberService memberService = new MemberServiceImpl(memberMapper);

        memberDto.setEmail("test@gmail.com");
        memberDto.setName("테스터");
        memberDto.setPassword("test1234!");
        memberDto.setNickname("테스터");
        memberDto.setGender("m");

        MemberDto memberDto1 = memberService.join(memberDto);
        System.out.println(memberDto1);
    }

}