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

    private String user_id;

    @NotEmpty
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*\\W).{8,16}", message = "비밀번호는 8~16자 영문 소문자와 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotEmpty
    private String nickname;

    @NotEmpty(message = "필수 입력 값입니다.")
    private String name, sex;

    private String interest1, interest2, interest3;

    public MemberDto toDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setUser_id(member.getUser_id());
        memberDto.setName(member.getName());
        memberDto.setEmail(member.getEmail());
        memberDto.setPassword(member.getPassword());
        memberDto.setSex(member.getSex());
        memberDto.setInterest1(member.getInterest1());
        memberDto.setInterest2(member.getInterest2());
        memberDto.setInterest3(member.getInterest3());
        memberDto.setNickname(member.getNickname());
        return memberDto;
    }

    public Member toEntity(String user_id, MemberDto memberDto) {
        return Member.builder()
                .user_id(user_id)
                .name(name)
                .email(email)
                .password(password)
                .sex(sex)
                .interest1(interest1)
                .interest2(interest2)
                .interest3(interest3)
                .nickname(nickname)
                .build();
    }
}
