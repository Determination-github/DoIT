package com.doit.study.profile.service;

import com.doit.study.mapper.MemberMapper;
import com.doit.study.mapper.ProfileMapper;
import com.doit.study.member.domain.Social;
import com.doit.study.member.service.SocialService;
import com.doit.study.profile.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService{

    private final ProfileMapper profileMapper;
    private final MemberMapper memberMapper;

    @Autowired
    @Qualifier("naverServiceImpl")
    private SocialService naverService;

    @Autowired
    @Qualifier("kakaoServiceImpl")
    private SocialService kakaoService;

    /**
     * 프로필 정보 가져오기
     * @param fileId
     * @return ProfileDto
     */
    @Override
    public ProfileDto findProfile(String fileId) {
        //프로필 정보 가져오기
        ProfileDto profileDto = profileMapper.findById(fileId);
        if(profileDto!=null) {
            return profileDto;
        } else {
            return null;
        }
    }

    /**
     * 이미지 찾기
     * @param id
     * @return String
     */
    @Override
    public String findImage(Integer id) {
        //이미지 경로 반환
        return profileMapper.getImagePath(id);
    }

    /**
     * 회원 정보 업데이트
     * @param profileDto
     * @throws Exception
     */
    @Override
    public void updateProfile(ProfileDto profileDto) throws Exception {
        int result;

        if(profileDto.getPassword() != null) { //패스워드에 따라 다른 update sql문 실행
            result = memberMapper.updateMemberWithPassword(profileDto);
        } else {
            result = memberMapper.updateMemberWithoutPassword(profileDto);
        }
    }

    /**
     * 회원 탈퇴
     * @param profileDto
     * @param request
     * @throws Exception
     */
    @Override
    public void deleteProfile(ProfileDto profileDto, HttpServletRequest request) throws Exception {
        //회원 정보 가져오기
        Integer result = memberMapper.checkSocialMember(profileDto.getId());

        if(result != null) { //회원정보가 있다면
            //social 회원 정보 가져오기
            Social social = memberMapper.findSocialMemberById(result);

            //social type 정보 가져오기
            String social_type = social.getSocial_type();

            //토큰 가져오기
            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("token");

            if(social_type.equals("naver")) { //네이버 회원 탈퇴
                memberMapper.deleteMemberById(result);
                naverService.deleteAccessToken(token);
            } else { //카카오 회원 탈퇴
                memberMapper.deleteMemberById(result);
                kakaoService.deleteAccessToken(token);
            }
        } else { //일반 회원 탈퇴
            log.info("id={}", profileDto.getId());
            memberMapper.deleteMemberById(profileDto.getId());
        }
    }
}
