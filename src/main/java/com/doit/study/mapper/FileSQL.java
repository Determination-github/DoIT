package com.doit.study.mapper;

public class FileSQL {

    public static final String insert =
            "INSERT INTO FI_INFO_TB(file_id, file_origin_name, file_path) " +
                    "VALUES ( #{file.file_id}, #{file.file_origin_name}, #{file.file_path})";

    public static final String findFile =
            "SELECT * FROM FI_INFO_TB WHERE FILE_ID = #{fileId}";

    public static final String findByStudyId =
            "SELECT * FROM FI_INFO_TB WHERE study_id = #{study_id}";

    public static final String updateStudyId =
            "UPDATE FI_INFO_TB SET study_id = #{study_id} WHERE FILE_ID = #{file_id}";

}
