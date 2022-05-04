package com.doit.study.mapper;

public class FileSQL {

    public static final String insert =
            "INSERT INTO FI_INFO_TB(file_id, file_origin_name, file_path) " +
                    "VALUES ( #{file.file_id}, #{file.file_origin_name}, #{file.file_path})";

    public static final String findFile =
            "SELECT * FROM FI_INFO_TB WHERE FILE_ID = #{fileId}";


}
