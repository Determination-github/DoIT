package com.doit.study.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class Social {

    @NotEmpty
    private String user_id, name, email, sex,
            interest1, interest2, interest3 ,nickname;

    @Builder
    public Social(String user_id, String name, String email, String sex,
                  String interest1, String interest2, String interest3, String nickname) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
        this.nickname = nickname;
    }
}
