package com.doit.study.profile.service;

import com.doit.study.mapper.MemberMapper;
import com.doit.study.mapper.ProfileMapper;
import com.doit.study.profile.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileMapper profileMapper;
    private final MemberMapper memberMapper;

    @Override
    public ProfileDto findFile(String fileId) {
        ProfileDto profileDto = profileMapper.findById(fileId);
        if(profileDto!=null) {
            return profileDto;
        }
        return null;
    }

    @Override
    public String findImage(Integer id) {
        String path = profileMapper.getImagePath(id);
        return path;
    }

    @Override
    public void updateProfile(ProfileDto profileDto) {
        int result;

        if(profileDto.getPassword() != null) {
            result = memberMapper.updateMemberWithPassword(profileDto);
        } else {
            result = memberMapper.updateMemberWithoutPassword(profileDto);
        }
    }
}
