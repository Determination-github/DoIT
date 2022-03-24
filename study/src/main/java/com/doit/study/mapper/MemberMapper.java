package com.doit.study.mapper;

<<<<<<< HEAD

import com.doit.study.member.domain.Member;
import org.apache.ibatis.annotations.*;

=======
import com.doit.study.member.domain.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;
>>>>>>> origin/jeongyong
import java.util.Optional;

@Mapper
public interface MemberMapper {

    //회원가입
    @Insert(MemberSQL.insert)
    Integer insert(@Param("member") Member member);
<<<<<<< HEAD

    //이메일로 아이디 찾기
    @Select(MemberSQL.findByEmail)
    @Results({
            @Result(property = "user_id", column = "USER_ID")
    })
    Optional<Member> findByEmail(@Param("email") String email);

    //닉네임 중복 체크
    @Select(MemberSQL.checkNickname)
    int checkNickname(@Param("nickname") String nickname);

    //이메일 중복 체크
    @Select(MemberSQL.checkEmail)
    int checkEmail(@Param("email") String email);

    int update(Member member);

    int delete(Member member);
=======

    @Select(MemberSQL.findByEmail)
    @Results({
            @Result(property = "user_id", column = "USER_ID")
    })
    Optional<Member> findByEmail(@Param("email") String email);

    List<Member> selectAll();

    int update(Member member);
>>>>>>> origin/jeongyong

    int delete(Member member);

}
