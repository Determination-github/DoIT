package com.doit.study.mapper;

public class CommentSQL {

    public static final String deleteAll =
            "DELETE FROM BO_COMMENT2_TB\n" +
            "WHERE  board_Id = #{board_Id}";

    public static final String count =
            "SELECT count(*) FROM BO_COMMENT2_TB\n" +
            "WHERE  board_Id = #{board_Id}";

    public static final String delete =
            "DELETE FROM BO_COMMENT2_TB " +
            "WHERE comment_Id = #{comment_Id} AND comment_Writer = #{comment_Writer}";

//    public static final String insert =
//            "INSERT INTO BO_COMMENT2_TB\n" +
//            "(comment_Id, parentComment_Id, board_Id, comment_Content, comment_Writer, reg_Date, update_Date)\n" +
//            "VALUES\n" +
//            "(comment_Id_seq.NEXTVAL, #{parentComment_Id}, #{board_Id}, #{comment_Content}, #{comment_Writer}, sysdate, sysdate)";

    public static final String selectAll =
            "SELECT comment_Id, board_Id, parentComment_Id, comment_Content, comment_Writer, reg_Date, update_Date\n" +
            "FROM BO_COMMENT2_TB\n" +
            "WHERE board_Id = #{board_Id}\n" +
            "ORDER BY reg_Date ASC, comment_Id ASC, parentComment_Id ASC";

    public static final String select =
            "SELECT comment_Id, board_Id, parentComment_Id, comment_Content, comment_Writer, reg_Date, update_Date\n" +
            "FROM BO_COMMENT2_TB\n" +
            "WHERE comment_Id = #{comment_Id}";

    public static final String update =
            "UPDATE BO_COMMENT2_TB\n" +
            "SET comment_Content = #{comment_Content}\n" +
            ", update_Date = sysdate\n" +
            "WHERE comment_Id = #{comment_Id} and comment_Writer = #{comment_Writer}";

    public static final String insert =
            "INSERT INTO SR_COMMENT_TB(study_id, writer_id, group_id, group_indent, comment) " +
            "VALUES(#{comment.study_id}, #{comment.writer_id}, IFNULL(#{comment.group_id}, null), " +
                    "IFNULL(#{comment.group_indent}, 0), #{comment.comment})";

    public static final String getComment =
            "SELECT * FROM SR_COMMENT_TB WHERE study_id = #{study_id} " +
                    "ORDER BY IF(ISNULL(group_id), comment_id, group_id)";

    public static final String getNickname =
            "SELECT nickname FROM USERS_TB WHERE id = #{comment.writer_id}";
}
