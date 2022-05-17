package com.doit.study.mapper;

public class CommentSQL {

    public static final String getCount =
            "SELECT count(*) FROM SR_COMMENT_TB\n" +
            "WHERE study_id = #{study_id}";


    public static final String insert =
            "INSERT INTO SR_COMMENT_TB(study_id, writer_id, group_id, group_indent, comment) " +
            "VALUES(#{comment.study_id}, #{comment.writer_id}, IFNULL(#{comment.group_id}, null), " +
                    "IFNULL(#{comment.group_indent}, 0), #{comment.comment})";

    public static final String getComment =
            "SELECT t1.comment_id,\n" +
                    "    t1.study_id,\n" +
                    "    t1.writer_id,\n" +
                    "    t1.group_id,\n" +
                    "    t1.group_indent,\n" +
                    "    t1.comment, " +
                    "    CASE WHEN t1.group_id IS NULL THEN LPAD(CONVERT(t1.comment_id, CHAR(4)), 4, '0')\n" +
                    "    ELSE CONCAT(LPAD(CONVERT(t1.group_id, CHAR(4)), 4, '0'), LPAD(CONVERT(t1.comment_id, CHAR(4)), 4, '0'))\n" +
                    "    END AS SEQ_CHAR\n" +
            "FROM SR_COMMENT_TB t1 " +
            "LEFT OUTER JOIN SR_COMMENT_TB t2 " +
            "ON t1.comment_id = t2.group_id " +
            "WHERE t1.study_id = #{study_id} " +
            "GROUP BY t1.comment " +
            "ORDER BY SEQ_CHAR";

    public static final String getNickname =
            "SELECT nickname FROM USERS_TB WHERE id = #{comment.writer_id}";

    public static final String modifyReply =
            "UPDATE SR_COMMENT_TB SET COMMENT = #{comment.comment}" +
                    " WHERE STUDY_ID = #{comment.study_id} " +
                    " AND COMMENT_ID = #{comment.comment_id} " +
                    " AND GROUP_ID = #{comment.group_id}";

    public static final String delete =
            "DELETE FROM SR_COMMENT_TB " +
                    " WHERE COMMENT_ID = #{comment_id} ";

}
