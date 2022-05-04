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
public class SocialDto {

    private int user_id;

    @NotEmpty
    private String socialId, socialEmail, socialName, socialNickname, socialGender;

    public SocialDto(String socialId,
                     String socialName,
                     String socialEmail,
                     String socialGender) {
        this.socialId = socialId;
        this.socialName = socialName;
        this.socialEmail = socialEmail;
        this.socialGender = socialGender;
    }

    public Member toEntity(SocialDto socialDto) {
        return Member.builder()
                .email(socialEmail)
                .name(socialName)
                .nickname(socialNickname)
                .gender(socialGender)
                .build();
    }

    public Social toSocial(int user_id, String socialId) {
        return Social.builder()
                .user_id(user_id)
                .social_id(socialId)
                .build();
    }
}
