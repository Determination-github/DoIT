package com.doit.study.mapper;

public class MemberSQL {

    public static final String insert =
            "INSERT INTO USERS_TB(email, name, nickname, password, gender) " +
            "VALUES ( #{member.email}, #{member.name}, #{member.nickname}, #{member.password}, #{member.gender} )";

    public static final String findByEmail =
            "SELECT * FROM USERS_TB WHERE email = #{email}";

    public static final String findBySocialId =
            "SELECT * FROM SO_USER_TB WHERE user_id = #{user_id}";

    public static final String checkNickname =
            "SELECT COUNT(NICKNAME) FROM USERS_TB WHERE nickname = #{nickname}";

    public static final String checkEmail =
            "SELECT COUNT(EMAIL) FROM USERS_TB WHERE email = #{email}";


    public static final String insertSocial =
            "INSERT INTO SO_USER_TB " +
            "VALUES( #{social.user_id}, #{social.name}, #{social.email}, " +
            "#{social.sex}, #{social.interest1}, #{social.interest2}, #{social.interest3}, #{social.nickname} )";

    public static final String findNicknameById =
            "SELECT NICKNAME FROM SO_USER_TB WHERE user_id = #{user_id}";

    public static final String findMember =
            "SELECT * FROM SO_USER_TB WHERE user_id = #{user_id}";

}
