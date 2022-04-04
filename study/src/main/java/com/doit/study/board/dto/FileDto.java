package com.doit.study.board.dto;

import com.doit.study.board.domain.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDto {

    private String fileId, originalFileName, filePath;

    public FileEntity toEntity(FileDto fileDto) {
        return FileEntity.builder()
                .file_id(fileId)
                .file_origin_name(originalFileName)
                .file_path(filePath)
                .build();
    }
}
