package com.doit.study.profile.service;

import com.doit.study.mapper.ProfileSQL;
import com.doit.study.profile.dto.ProfileDto;

public interface ProfileService {

    ProfileDto findFile(String fileId);

    String findImage(Integer id);

    void updateProfile(ProfileDto profileDto);
}
