package com.doit.study.member.domain.category;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 3차 카테고리
 * java : 자바
 * spring : 스프링
 */
@Data
@AllArgsConstructor
public class ThirdInterestCategory {

    private String code;
    private String category;
}
