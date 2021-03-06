package com.doit.study.mapper;

public class BoardSQL {

    public static final String count =
            "SELECT COUNT(*) FROM SR_MOIM_TB WHERE DATE_FORMAT(NOW(), '%Y-%m-%d') <= schedule_end";

    public static final String selectPage =
            "SELECT * FROM SR_MOIM_TB WHERE DATE_FORMAT(NOW(), '%Y-%m-%d') <= schedule_end ORDER BY study_id DESC LIMIT ${pagination.firstRecordIndex} , ${pagination.countPerPage}";

    public static final String selectPageById =
            "SELECT * FROM SR_MOIM_TB WHERE id = ${id} ORDER BY study_id DESC LIMIT ${pagination.firstRecordIndex} , ${pagination.countPerPage}";

    public static final String selectWishPageAll =
            "<script> " +
            "SELECT * FROM SR_MOIM_TB WHERE study_id IN " +
                    "<foreach collection='wishlist' item='wishlist' open='(' separator=',' close=')'> " +
                        "#{wishlist.study_id} " +
                    "</foreach> " +
                    "ORDER BY study_id DESC LIMIT ${pagination.firstRecordIndex} , ${pagination.countPerPage}" +
            "</script>";

    public static final String getLastBoard =
            "SELECT * FROM SR_MOIM_TB WHERE id = #{id} ORDER BY study_id DESC LIMIT 1";

    public static final String insertBoard =
            "insert into SR_MOIM_TB (id, schedule_start, schedule_end, " +
                    "title, content, location, on_off, " +
                    "interest1, interest2, interest3) " +
                    "values (#{board.id}, #{board.schedule_start}, #{board.schedule_end}, " +
                    "#{board.title}, #{board.content}, " +
                    "#{board.location}, #{board.on_off}, #{board.interest1}, " +
                    "#{board.interest2}, #{board.interest3})";

    public static final String increaseViewCount =
            "UPDATE SR_MOIM_TB SET view_count = view_count + 1 WHERE study_id = #{study_id}";

    public static final String getBoard =
            "SELECT * FROM SR_MOIM_TB WHERE study_id = #{study_id}";

    //검색어로 스터디 글 개수 가져오기
    public static final String getOnlineBoardCountByKeyword =
            "SELECT count(*) FROM " +
                    "(SELECT * FROM SR_MOIM_TB WHERE on_off = #{searchDto.on_off}) sub" +
                    " WHERE sub.title like IFNULL(CONCAT('%',#{searchDto.keyword},'%'), '%%') " +
                    "OR sub.content like IFNULL(CONCAT('%',#{searchDto.keyword},'%'), '%%')";

    //검색어로 스터디 글 개수 가져오기
    public static final String getOfflineBoardCountByKeyword =
            "SELECT count(*) FROM " +
                    "(SELECT * FROM SR_MOIM_TB WHERE on_off = #{searchDto.on_off} and location like IFNULL(CONCAT('%',#{searchDto.location},'%'), '%%')) sub" +
                    " WHERE sub.title like IFNULL(CONCAT('%',#{searchDto.keyword},'%'), '%%') " +
                    "OR sub.content like IFNULL(CONCAT('%',#{searchDto.keyword},'%'), '%%')";

    //글 수정하기
    public static final String updateBoard =
            "UPDATE SR_MOIM_TB SET title = #{board.title}, content = #{board.content}, location = #{board.location}, on_off = #{board.on_off}, " +
                    "interest1 = #{board.interest1}, interest2 = #{board.interest2}, interest3 = #{board.interest3}, " +
                    "schedule_start = #{board.schedule_start}, schedule_end = #{board.schedule_end} " +
                    "WHERE study_id = #{board.study_id}";

    //글 삭제하기
    public static final String deleteBoard =
            "DELETE FROM SR_MOIM_TB WHERE study_id = #{study_id}";

    //게시글 개수 조회 by id
    public static final String getCountById =
            "SELECT COUNT(*) FROM SR_MOIM_TB WHERE id = #{id}";

    //검색어로 오프라인 스터디 글 가져오기
    public static final String getOfflineBoardByKeyword =
            "SELECT * FROM " +
                    "(SELECT * FROM SR_MOIM_TB WHERE on_off = #{searchDto.on_off} and location like IFNULL(CONCAT('%',#{searchDto.location},'%'), '%%')) sub" +
                    " WHERE sub.title like IFNULL(CONCAT('%',#{searchDto.keyword},'%'), '%%') " +
                    "OR sub.content like IFNULL(CONCAT('%',#{searchDto.keyword},'%'), '%%') " +
                    "AND DATE_FORMAT(NOW(), '%Y-%m-%d') <= schedule_end ORDER BY study_id DESC LIMIT ${pagination.firstRecordIndex} , ${pagination.countPerPage}";

    //검색어로 온라인 스터디 글 가져오기
    public static final String getOnlineBoardByKeyword =
            "SELECT * FROM " +
                    "(SELECT * FROM SR_MOIM_TB WHERE on_off = #{searchDto.on_off}) sub" +
                    " WHERE sub.title like IFNULL(CONCAT('%',#{searchDto.keyword},'%'), '%%') " +
                    "OR sub.content like IFNULL(CONCAT('%',#{searchDto.keyword},'%'), '%%') " +
                    "AND DATE_FORMAT(NOW(), '%Y-%m-%d') <= schedule_end ORDER BY study_id DESC LIMIT ${pagination.firstRecordIndex} , ${pagination.countPerPage}";

    public static final String getMyStudyList =
            "SELECT COUNT (*) FROM SR_MOIM_TB WHERE user_id = #{user_id}";


}
