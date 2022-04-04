package com.doit.study.mapper;

public class FileBoardSQL {

    public static final String insert =
            "INSERT INTO SR_FILE_TB " +
                    "VALUES ( #{fileBoard.file_id}, #{fileBoard.study_id})";

}
