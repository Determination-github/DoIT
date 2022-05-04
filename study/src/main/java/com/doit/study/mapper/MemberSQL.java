package com.doit.study.mapper;

public class MemberSQL {

    public static final String insert =
            "INSERT INTO USERS_TB(email, name, nickname, password, gender) " +
            "VALUES ( #{member.email}, #{member.name}, #{member.nickname}, #{member.password}, #{member.gender} )";

    public static final String insertSocialMemberToUser =
            "INSERT INTO USERS_TB(email, name, nickname, gender) " +
            "VALUES ( #{member.email}, #{member.name}, #{member.nickname}, #{member.gender} )";

    public static final String lastIndexOfId =
            "SELECT LAST_INSERT_ID()";

    public static final String insertSocialTB =
            "INSERT INTO SOCIAL_USERS_TB(id, social_id) " +
            "VALUES ( #{social.user_id}, #{social.social_id} )";

    public static final String findBySocialId =
            "SELECT * FROM USERS_TB " +
            "WHERE id = " +
            "(SELECT id FROM SOCIAL_USERS_TB WHERE social_id = #{social_id})";

    public static final String findNicknameById =
            "SELECT NICKNAME FROM USERS_TB WHERE id = #{id}";





    public static final String getMember =
            "SELECT * FROM USERS_TB WHERE id = #{id}";

    public static final String findByEmail =
            "SELECT * FROM USERS_TB WHERE email = #{email}";

    public static final String checkNickname =
            "SELECT COUNT(NICKNAME) FROM USERS_TB WHERE nickname = #{nickname}";

    public static final String checkEmail =
            "SELECT COUNT(EMAIL) FROM USERS_TB WHERE email = #{email}";

    public static final String findMember =
            "SELECT * FROM SOCIAL_USERS_TB WHERE user_id = #{user_id}";

}
