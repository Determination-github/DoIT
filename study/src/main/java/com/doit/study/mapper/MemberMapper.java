package com.doit.study.mapper;

import com.doit.study.member.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MemberMapper {

    @Insert(MemberSQL.insert)
    public Integer insert(@Param("member") final Member member);

    public List<Member> selectAll() throws Exception;

    public List<Member> findById(String id) throws Exception;

    public int update(Member member) throws Exception;

    public int delete(Member member) throws Exception;



}
