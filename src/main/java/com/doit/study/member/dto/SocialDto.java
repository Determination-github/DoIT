package com.doit.study.member.dto;

import com.doit.study.member.domain.Member;
import com.doit.study.member.domain.Social;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialDto implements Serializable {

    private Integer user_id;

    @NotEmpty
    private String token, socialId, socialEmail, socialName, socialNickname, socialGender;

    private String social_type;

    public SocialDto(String token,
                     String socialId,
                     String socialName,
                     String socialEmail,
                     String socialGender) {
        this.token = token;
        this.socialId = socialId;
        this.socialName = socialName;
        this.socialEmail = socialEmail;
        this.socialGender = socialGender;
    }

    public SocialDto(
                     String socialId,
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

    public Social toSocial(Integer user_id, String socialId, String social_type, String token) {
        return Social.builder()
                .user_id(user_id)
                .social_id(socialId)
                .social_type(social_type)
                .token(token)
                .build();
    }
}
