package com.doit.study.board.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class FileEntity {

    @NotEmpty
    private String file_id, file_origin_name, file_path;

    @Builder
    public FileEntity(String file_id, String file_origin_name, String file_path) {
        this.file_id = file_id;
        this.file_origin_name = file_origin_name;
        this.file_path = file_path;
    }

}
