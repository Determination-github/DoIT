package com.doit.study.file.dto;

import com.doit.study.file.domain.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private String file_id, file_origin_name, file_path;
    private Date file_reg_date;

    public FileEntity toEntity(FileDto fileDto) {
        return FileEntity.builder()
                .file_id(file_id)
                .file_origin_name(file_origin_name)
                .file_path(file_path)
                .build();
    }
}
