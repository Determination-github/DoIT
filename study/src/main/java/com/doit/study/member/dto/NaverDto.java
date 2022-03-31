package com.doit.study.member.dto;

import com.doit.study.member.domain.Social;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverDto {

    @NotEmpty
    private String naverId, naverName, naverInterest1, naverInterest2, naverInterest3,
            naverEmail, naverGender, naverNickname;

    public NaverDto(String id,
                    String naverName,
                    String naverEmail,
                    String naverGender) {
        this.naverId = id;
        this.naverName = naverName;
        this.naverEmail = naverEmail;
        this.naverGender = naverGender;
    }

    public NaverDto toDto(Social social) {
        NaverDto naverDto = new NaverDto();
        naverDto.setNaverId(social.getUser_id());
        naverDto.setNaverName(social.getName());
        naverDto.setNaverInterest1(social.getInterest1());
        naverDto.setNaverInterest2(social.getInterest2());
        naverDto.setNaverInterest3(social.getInterest3());
        naverDto.setNaverEmail(social.getEmail());
        naverDto.setNaverGender(social.getSex());
        naverDto.setNaverNickname(social.getNickname());
        return naverDto;
    }

    public Social toEntity(NaverDto naverDto) {
        return Social.builder()
                .user_id(naverId)
                .name(naverName)
                .email(naverEmail)
                .sex(naverGender)
                .interest1(naverInterest1)
                .interest2(naverInterest2)
                .interest3(naverInterest3)
                .nickname(naverNickname)
                .build();
    }
}
