package com.doit.study.member.service;

import com.doit.study.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MemberServiceImplTest {

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    @Mock
    private MemberService memberService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }


}