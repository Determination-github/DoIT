package com.doit.study.profile.service;

import com.doit.study.mapper.MemberMapper;
import com.doit.study.mapper.ProfileMapper;
import com.doit.study.member.SessionConst;
import com.doit.study.member.domain.Member;
import com.doit.study.member.domain.Social;
import com.doit.study.member.service.KakaoService;
import com.doit.study.member.service.NaverService;
import com.doit.study.profile.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileMapper profileMapper;
    private final MemberMapper memberMapper;
    private final NaverService naverService;
    private final KakaoService kakaoService;

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

    @Override
    public void deleteProfile(ProfileDto profileDto, HttpServletRequest request) {
        Integer result = memberMapper.checkSocialMember(profileDto.getId());
        log.info("회원 아이디는 {}", result);

        if(result != null) {
            Social social = memberMapper.findSocialMemberById(result);

            String social_type = social.getSocial_type();
            log.info("type = " + social_type);

            //토큰 가져오기
            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("token");
            log.info("token = " + token);

            if(social_type.equals("naver")) {
                memberMapper.deleteMemberById(result);
                naverService.deleteAccessToken(token);
            } else {
                memberMapper.deleteMemberById(result);
                kakaoService.unlinkKakao(token);
            }
        } else {
            memberMapper.deleteMemberById(result);
        }
    }
}
