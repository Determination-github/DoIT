package com.doit.study.member.dto;

import com.doit.study.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {

    private  String user_id;

    private String name, email, password, sex, address, interest1, interest2, interest3, nickname;

    public void toDto(Member member) {
        this.user_id = member.getUser_id();
        this.name = member.getName();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.sex = member.getSex();
        this.address = member.getAddress();
        this.interest1 = member.getInterest1();
        this.interest2 = member.getInterest2();
        this.interest3 = member.getInterest3();
        this.nickname = member.getNickname();
    }

    public Member toEntity(String user_id, MemberDto memberDto) {
        return Member.builder()
                .user_id(user_id)
                .name(name)
                .email(email)
                .password(password)
                .sex(sex)
                .address(address)
                .interest1(interest1)
                .interest2(interest2)
                .interest3(interest3)
                .nickname(nickname)
                .build();
    }
}
