package com.doit.study.mapper;

public class ProfileSQL {

    public static final String insert =
            "INSERT INTO USERS_PROFILE_TB(id, file_id, file_origin_name, file_path) " +
                    "VALUES (#{file.id}, #{file.file_id}, #{file.file_origin_name}, #{file.file_path})";

    public static final String findFile =
            "SELECT * FROM USERS_PROFILE_TB WHERE FILE_ID = #{fileId}";

    public static final String findImagePath =
            "SELECT file_path FROM USERS_PROFILE_TB WHERE id = #{id}";

    public static final String deleteProfile =
            "DELETE FROM USERS_PROFILE_TB WHERE id = #{id}";


}
