package com.doit.study.profile.service;

import com.doit.study.profile.dto.ProfileDto;

import javax.servlet.http.HttpServletRequest;

public interface ProfileService {

    //프로필 정보 가져오기
    ProfileDto findProfile(String fileId);

    //이미지 찾기
    String findImage(Integer id);

    //회원 정보 업데이트
    void updateProfile(ProfileDto profileDto) throws Exception;

    //회원 탈퇴
    void deleteProfile(ProfileDto profileDto, HttpServletRequest request) throws Exception;
}
