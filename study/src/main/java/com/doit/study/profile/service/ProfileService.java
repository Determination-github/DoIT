package com.doit.study.profile.service;

import com.doit.study.profile.dto.ProfileDto;

import javax.servlet.http.HttpServletRequest;

public interface ProfileService {

    ProfileDto findFile(String fileId);

    String findImage(Integer id);

    void updateProfile(ProfileDto profileDto);

    void deleteProfile(ProfileDto profileDto, HttpServletRequest request);
}
