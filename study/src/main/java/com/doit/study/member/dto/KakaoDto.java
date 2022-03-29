package com.doit.study.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class KakaoDto {

    @NotEmpty
    private String kakaoName, accessToken;

    private String kakaoEmail, kakaoGender;

}
