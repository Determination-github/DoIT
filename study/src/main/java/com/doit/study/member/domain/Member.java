package com.doit.study.member.domain;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class Member {

    @NotEmpty
    private String user_id, name, email, password, sex,
                interest1, interest2, interest3 ,nickname;

    @Builder
    public Member(String user_id, String name, String email, String password, String sex,
                  String interest1, String interest2, String interest3, String nickname) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
        this.nickname = nickname;
    }
}
