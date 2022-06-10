package com.doit.study.member.dto;

import com.doit.study.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Integer id;

    private String social_id;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*\\W).{8,16}", message = "비밀번호는 8~16자 영문 소문자와 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "성별을 입력해주세요.")
    private String gender;

    public MemberDto(  String email,
                       String nickname,
                       String name,
                       String gender
    ) {
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.gender = gender;
    }


    public MemberDto toDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setName(member.getName());
        memberDto.setEmail(member.getEmail());
        memberDto.setPassword(member.getPassword());
        memberDto.setGender(member.getGender());
        memberDto.setNickname(member.getNickname());
        return memberDto;
    }

    public Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .password(password)
                .gender(gender)
                .build();
    }
}
