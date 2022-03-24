package com.doit.study.member.domain.category;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *1차 카테고리
 *back : 백엔드
 *front : 프론트엔드
 *
 */
@Data
@AllArgsConstructor
public class FirstInterestCategory {

    private String code;
    private String category;

}
