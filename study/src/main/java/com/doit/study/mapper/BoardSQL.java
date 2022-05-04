package com.doit.study.mapper;

public class BoardSQL {

    public static final String count =
            "SELECT COUNT(*) FROM SR_MOIM_TB";

    public static final String selectPage =
            "SELECT * FROM SR_MOIM_TB ORDER BY study_id DESC LIMIT ${pagination.firstRecordIndex} , ${pagination.countPerPage}";

    public static final String insertBoard =
            "insert into SR_MOIM_TB (id, schedule_start, schedule_end, " +
                    "title, content, sub_title, location, on_off, " +
                    "interest1, interest2, interest3) " +
                    "values (#{board.id}, #{board.schedule_start}, #{board.schedule_end}, " +
                    "#{board.title}, #{board.content}, #{board.sub_title}, " +
                    "#{board.location}, #{board.on_off}, #{board.interest1}, " +
                    "#{board.interest2}, #{board.interest3})";

    public static final String increaseViewCount =
            "UPDATE SR_MOIM_TB SET view_count = view_count + 1 WHERE study_id = #{study_id}";

    public static final String getBoard =
            "SELECT * FROM SR_MOIM_TB WHERE study_id = #{study_id}";

    public static final String deleteAll =
            "DELETE FROM BO_STUDY_TB";

    public static final String delete =
            "DELETE FROM BO_STUDY_TB WHERE board_Id = #{board_Id}";

    public static final String insert =
            "insert into BO_STUDY_TB (board_Id, board_Title, board_SubTitle, board_Content, board_Writer) " +
            "values (board_id_seq.NEXTVAL, #{board_Title}, #{board_SubTitle}, #{board_Content}, #{board_Writer})";








    public static final String selectAll =
            "SELECT board_Id, board_Title, board_Writer, board_SubTitle, board_Content, board_Count, board_Comment, to_char(board_date,'YYYYMMDD') " +
            "FROM BO_STUDY_TB " +
            "ORDER BY BOARD_ID DESC";


    public static final String select =
            "SELECT board_Id, board_Title, board_SubTitle, board_Content, board_Count " +
                    "FROM BO_STUDY_TB " +
                    "WHERE board_Id = #{board_Id}";

    public static final String findNickname =
            "SELECT nickname FROM WHERE user_id = #{user_id}";


    public static final String update =
            "UPDATE BO_STUDY_TB " +
            "SET board_Title = #{board_Title}" +
            ", board_SubTitle = #{board_SubTitle} " +
            ", board_Content = #{board_Content} " +
            "WHERE board_Id = #{board_Id}";


    public static final String getMyStudyList =
            "SELECT COUNT (*) FROM SR_MOIM_TB WHERE user_id = #{user_id}";


    public static final String searchResultCount =
    "select COUNT (*) from BO_STUDY_TB\n" +
            "                    where board_Title like'%' || #{board_Title} || '%'";

    public static final String updateCommentCount =
            "UPDATE SR_MOIM_TB " +
                    "SET comment_count = comment_count + #{count} " +
                    "where study_id = #{study_id}";

}
