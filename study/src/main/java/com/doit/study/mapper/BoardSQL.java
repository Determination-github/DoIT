package com.doit.study.mapper;

public class BoardSQL {

    public static final String count =
            "SELECT COUNT (*) FROM BO_STUDY_TB";

    public static final String deleteAll =
            "DELETE FROM BO_STUDY_TB";

    public static final String delete =
            "DELETE FROM BO_STUDY_TB WHERE board_Id = #{board_Id}";

    public static final String insert =
            "insert into BO_STUDY_TB (board_Id, board_Title, board_SubTitle, board_Content, board_Count, " +
                    "board_Comment, board_RegDate, board_Writer) " +
                    "values (board_id_seq.NEXTVAL, #{board_Title}, #{board_SubTitle}, #{board_Content}, '0', '0', sysdate, 'Hodong')";

    public static final String selectAll =
            "SELECT board_Id, board_Title, board_SubTitle, board_Content, board_Count, board_Comment, to_char(board_date,'YYYYMMDD') " +
                    "FROM BO_STUDY_TB " +
                    "ORDER BY BOARD_ID DESC";

    public static final String select =
            "SELECT board_Id, board_Title, board_SubTitle, board_Content, board_Count " +
                    "FROM BO_STUDY_TB " +
                    "WHERE board_Id = #{board_Id}";

    public static final String selectPage =
            "SELECT board_Id, board_Title, board_SubTitle, board_Count, board_Comment, board_RegDate\n" +
                    "FROM (\n" +
                    "         SELECT rownum rnum, A.*\n" +
                    "         from (\n" +
                    "                  select board_Id, board_Title, board_SubTitle, board_Count, board_Comment, board_RegDate\n" +
                    "                  from BO_STUDY_TB\n" +
                    "                  order by board_Id desc\n" +
                    "              ) A\n" +
                    "     )\n" +
                    "where rnum > ${firstRecordIndex} AND rnum <= ${lastRecordIndex}";

    public static final String update =
            "UPDATE BO_STUDY_TB " +
                    "SET board_Title = #{board_Title}" +
                    ", board_SubTitle = #{board_SubTitle} " +
                    ", board_Content = #{board_Content} " +
                    "WHERE board_Id = #{board_Id}";

    public static final String increaseViewCount =
            "UPDATE BO_STUDY_TB " +
                    "SET board_Count = board_Count + 1 " +
                    "WHERE board_Id = #{board_Id}";

    public static final String searchSelectPage =
//            "SELECT board_Id, board_Title, board_SubTitle, board_Count, board_Comment, board_date\n" +
//                    "FROM (\n" +
//                    "SELECT rownum rnum, A.*\n" +
//                    " from (\n" +
//                    "  select board_Id, board_Title, board_SubTitle, board_Count, board_Comment, board_date\n" +
//                    "  from BO_STUDY_TB\n" +
//                    "  where board_Title like '${board_Title}'\n" +
//                    " order by board_Id desc\n" +
//                    "  ) A\n" +
//                    " )\n" +
//                    " where rnum > ${firstRecordIndex} AND rnum <= ${lastRecordIndex}";
            "SELECT board_Id, board_Title, board_SubTitle, board_Count, board_Comment, board_date\n" +
                    "FROM (\n" +
                    "SELECT rownum rnum, A.*\n" +
                    " from (\n" +
                    "  select board_Id, board_Title, board_SubTitle, board_Count, board_Comment, board_date\n" +
                    "  from BO_STUDY_TB\n" +
                    "  where board_Title like'%' || #{board_Title} || '%'\n" +
                    " order by board_Id desc\n" +
                    "  ) A\n" +
                    " )\n" +
                    " where rnum > ${firstRecordIndex} AND rnum <= ${lastRecordIndex}";

    public static final String searchResultCount =
//            "select COUNT (*) from BO_STUDY_TB " +
//                    "where board_Title like #{board_Title}";
            "select COUNT (*) from BO_STUDY_TB\n" +
                    "                    where board_Title like'%' || #{board_Title} || '%'";

}