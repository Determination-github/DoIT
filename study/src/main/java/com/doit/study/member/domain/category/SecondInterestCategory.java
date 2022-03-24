package com.doit.study.member.domain.category;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *2차 카테고리
 *
 * study : 스터디
 * project : 프로젝트
 * cert : 자격증
 *
 */
@Data
@AllArgsConstructor
public class SecondInterestCategory {

    private String code;
    private String category;
}
