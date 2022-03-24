package com.doit.study.mapper;

public class MemberSQL {

    public static final String insert =
            "INSERT INTO USER_TB " +
<<<<<<< HEAD
            "VALUES(#{member.user_id}, #{member.name}, #{member.email}, #{member.password}, " +
            "#{member.sex}, #{member.interest1}, #{member.interest2}, #{member.interest3}, #{member.nickname} )";
=======
                    "VALUES(#{member.user_id}, #{member.name}, #{member.email}, #{member.password}, #{member.address}, " +
                    "#{member.sex}, #{member.interest1}, #{member.interest2}, #{member.interest3}, #{member.nickname} )";
>>>>>>> origin/jeongyong


    public static final String findByEmail =
            "SELECT * FROM USER_TB WHERE email = #{email}";
<<<<<<< HEAD

    public static final String checkNickname =
            "SELECT COUNT(NICKNAME) FROM USER_TB WHERE nickname = #{nickname}";

    public static final String checkEmail =
            "SELECT COUNT(EMAIL) FROM USER_TB WHERE email = #{email}";


=======
>>>>>>> origin/jeongyong
}
