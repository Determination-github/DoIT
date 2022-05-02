package com.doit.study.member.dto;

import com.doit.study.member.domain.Member;
import com.doit.study.member.domain.Social;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverDto {

    private int user_id;

    @NotEmpty
    private String naverId, naverEmail, naverName, naverNickname, naverGender;

    public NaverDto(String naverId,
                    String naverName,
                    String naverEmail,
                    String naverGender) {
        this.naverId = naverId;
        this.naverName = naverName;
        this.naverEmail = naverEmail;
        this.naverGender = naverGender;
    }

    public Member toEntity(NaverDto naverDto) {
        return Member.builder()
                .email(naverEmail)
                .name(naverName)
                .nickname(naverNickname)
                .gender(naverGender)
                .build();
    }

    public Social toSocial(int user_id, String naverId) {
        return Social.builder()
                .user_id(user_id)
                .social_id(naverId)
                .build();
    }
}
