package com.doit.study.member.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 성별
 * M : 남자
 * F : 여자
 */
@Data
@AllArgsConstructor
public class Gender {

    private String code;
    private String gender;
}
