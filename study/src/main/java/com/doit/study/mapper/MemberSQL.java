package com.doit.study.mapper;

public class MemberSQL {

    public static final String insert =
            "INSERT INTO USER_TB " +
            "VALUES ( #{member.user_id}, #{member.name}, #{member.email}, #{member.password}, " +
            "#{member.sex}, #{member.interest1}, #{member.interest2}, #{member.interest3}, #{member.nickname} )";

    public static final String findByEmail =
            "SELECT * FROM USER_TB WHERE email = #{email}";

    public static final String findBySocialId =
            "SELECT * FROM USER_TB_SOCIAL WHERE user_id = #{user_id}";

    public static final String checkNickname =
            "SELECT COUNT(NICKNAME) FROM USER_TB WHERE nickname = #{nickname}";

    public static final String checkEmail =
            "SELECT COUNT(EMAIL) FROM USER_TB WHERE email = #{email}";


    public static final String insertSocial =
            "INSERT INTO USER_TB_SOCIAL " +
            "VALUES( #{social.user_id}, #{social.name}, #{social.email}, " +
            "#{social.sex}, #{social.interest1}, #{social.interest2}, #{social.interest3}, #{social.nickname} )";


}
