package com.doit.study.mapper;

public class MemberSQL {

    public static final String insert =
            "INSERT INTO USER_TB " +
                    "VALUES(#{member.user_id}, #{member.name}, #{member.email}, #{member.password}, #{member.address}, " +
                    "#{member.sex}, #{member.interest1}, #{member.interest2}, #{member.interest3}, #{member.nickname} )";


    public static final String findByEmail =
            "SELECT * FROM USER_TB WHERE email = #{email}";
}
