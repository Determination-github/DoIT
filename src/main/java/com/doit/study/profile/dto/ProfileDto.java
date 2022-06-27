package com.doit.study.profile.dto;

import com.doit.study.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Integer id, size;
    private String email, name ,nickname, password;
    private String file_id, file_origin_name, file_path;
    private Date file_reg_date;

    public ProfileDto(Integer id, String email, String name, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
    }

    public Profile toEntity(ProfileDto profileDto) {
        return Profile.builder()
                .id(id)
                .file_id(file_id)
                .file_origin_name(file_origin_name)
                .file_path(file_path)
                .build();
    }
}
