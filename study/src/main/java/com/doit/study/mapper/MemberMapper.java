package com.doit.study.mapper;

import com.doit.study.member.domain.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Insert(MemberSQL.insert)
    Integer insert(@Param("member") Member member);

    @Select(MemberSQL.findByEmail)
    @Results({
            @Result(property = "user_id", column = "USER_ID")
    })
    Optional<Member> findByEmail(@Param("email") String email);

    List<Member> selectAll();

    int update(Member member);

    int delete(Member member);

}
