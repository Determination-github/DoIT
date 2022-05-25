package com.doit.study.mapper;


import com.doit.study.member.domain.Member;
import com.doit.study.member.domain.Social;
import com.doit.study.profile.dto.ProfileDto;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    //회원가입
    @Insert(MemberSQL.insert)
    Integer insert(@Param("member") Member member);

    //Social 회원가입
    @Insert(MemberSQL.insertSocialMemberToUser)
    Integer insertSocialToUser(@Param("member") Member member);

    //id 가져오기
    @Select(MemberSQL.lastIndexOfId)
    int findLastId();

    //social 회원 회원가입
    @Insert(MemberSQL.insertSocialTB)
    Integer insertSocial(@Param("social") Social social);

    //social 회원 정보 찾기
    @Select(MemberSQL.findBySocialId)
    Optional<Member> findSocialMemberById(@Param("social_id") String id);

    //아이디로 회원 닉네임 찾기
    @Select(MemberSQL.findNicknameById)
    String findNickname(@Param("id") Integer id);

    //회원 찾기
    @Select(MemberSQL.findMember)
    Member findMember(@Param("id") Integer id);

    @Select(MemberSQL.getMember)
    Member getMemberInfoById(@Param("id") int id);

    @Select(MemberSQL.findByEmail)
    @Results({
            @Result(property = "user_id", column = "USER_ID")
    })
    Optional<Member> findByEmail(@Param("email") String email);

    @Select(MemberSQL.checkNickname)
    int checkNickname(@Param("nickname") String nickname);

    @Select(MemberSQL.checkEmail)
    int checkEmail(@Param("email") String email);

    @Update(MemberSQL.updateMemberWithPassword)
    int updateMemberWithPassword(@Param("profileDto")ProfileDto profileDto);

    @Update(MemberSQL.updateMemberWithoutPassword)
    int updateMemberWithoutPassword(@Param("profileDto")ProfileDto profileDto);


    int update(Member member);

    int delete(Member member);



}
