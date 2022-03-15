package com.doit.study.member.domain;

import com.doit.study.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class Member {

    @NotEmpty
    private String user_id;

    @NotEmpty
    private String name, email, password, sex, address, interest1, interest2, interest3, nickname;
//
    @Builder
    public Member(String user_id, String name, String email, String password, String sex, String address,
                  String interest1, String interest2, String interest3, String nickname) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.address = address;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
        this.nickname = nickname;
    }
}
