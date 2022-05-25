package com.doit.study.profile.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class Profile {

    private Integer id;

    private String file_id, file_origin_name, file_path;

    private Date file_reg_date;

    @Builder
    public Profile(Integer id, String file_id, String file_origin_name, String file_path) {
        this.id = id;
        this.file_id = file_id;
        this.file_origin_name = file_origin_name;
        this.file_path = file_path;
    }
}
