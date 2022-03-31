package com.doit.study.member.dto;

import com.doit.study.member.domain.Social;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoDto {

    @NotEmpty
    private String kakaoId, kakaoName, accessToken,
            kakaoInterest1, kakaoInterest2, kakaoInterest3,
            kakaoEmail, kakaoGender, kakaoNickname;

    public KakaoDto(String id,
                    String kakaoName,
                    String accessToken,
                    String kakaoEmail,
                    String kakaoGender) {
        this.kakaoId = id;
        this.kakaoName = kakaoName;
        this.accessToken = accessToken;
        this.kakaoEmail = kakaoEmail;
        this.kakaoGender = kakaoGender;
    }

    public KakaoDto toDto(Social social) {
        KakaoDto kakaoDto = new KakaoDto();
        kakaoDto.setKakaoId(social.getUser_id());
        kakaoDto.setKakaoName(social.getName());
        kakaoDto.setKakaoInterest1(social.getInterest1());
        kakaoDto.setKakaoInterest2(social.getInterest2());
        kakaoDto.setKakaoInterest3(social.getInterest3());
        kakaoDto.setKakaoEmail(social.getEmail());
        kakaoDto.setKakaoGender(social.getSex());
        kakaoDto.setKakaoNickname(social.getNickname());
        return kakaoDto;
    }

    public Social toEntity(KakaoDto kakaoDto) {
        return Social.builder()
                .user_id(kakaoId)
                .name(kakaoName)
                .email(kakaoEmail)
                .sex(kakaoGender)
                .interest1(kakaoInterest1)
                .interest2(kakaoInterest2)
                .interest3(kakaoInterest3)
                .nickname(kakaoNickname)
                .build();
    }

}
