package com.doit.study.member.domain;

import lombok.*;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class Member {

    private Integer user_id;

    @NotEmpty
    private String name, email, gender, password, nickname;

    @Builder
    public Member(String email, String name, String nickname,
                  String password, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.nickname = nickname;
    }
}
