package com.doit.study.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class Social {

    private int user_id;

    @NotEmpty
    private String social_id;

    @Builder
    public Social(int user_id, String social_id) {
        this.user_id = user_id;
        this.social_id = social_id;
    }
}
