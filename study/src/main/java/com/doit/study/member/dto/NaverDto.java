package com.doit.study.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class NaverDto {

    @NotEmpty
    private String naverName, naverEmail, naverGender;

}
