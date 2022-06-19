package com.doit.study.profile.controller;

import com.doit.study.board.service.BoardService;
import com.doit.study.file.service.S3Uploader;
import com.doit.study.profile.dto.ProfileDto;
import com.doit.study.member.service.MemberService;
import com.doit.study.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final MemberService memberService;
    private final ProfileService profileService;
    private final BoardService boardService;
    private final S3Uploader s3Uploader;

    /**
     * 프로필 정보 가져오기
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Integer id, Model model) {
        //프로필 객체 생성
        ProfileDto profileDto = new ProfileDto();

        //프로필 정보 가져오기
        profileDto = memberService.findMember(id);

        //게시글 개수 가져오기
        Integer size = boardService.getCountById(id);
        profileDto.setSize(size);

        //프로파일 이미지 경로 설정
        String path = profileService.findImage(id);
        profileDto.setFile_path(path);

        model.addAttribute("profileDto", profileDto);

        return "/members/profile";
    }

    /**
     * 프로필 이미지 업로드
     * @param file
     * @param id
     * @return ResponseEntity
     * @throws Exception
     */
    @PostMapping("/profile/{id}")
    public ResponseEntity uploadProfile(@RequestParam("file") MultipartFile file,
                                           @PathVariable Integer id) throws Exception {
        log.info("MultipartFile={}", file);

        ProfileDto profileDto = new ProfileDto();
        //프로필 이미지 업로드
        s3Uploader.profileUpload(file, "profileImageUpload", profileDto, id);

        //프로필 정보 찾기
        String fileId = profileDto.getFile_id();
        profileDto = profileService.findProfile(fileId);

        //프로필 이미지 경로 세팅
        Map<String, String> path = new HashMap<>();
        path.put("path", profileDto.getFile_path());

        return ResponseEntity.ok().body(path);
    }

    /**
     * 프로필 수정화면 이동
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/profile/edit/{id}")
    public String editProfile(@PathVariable Integer id,
                              Model model) {
        ProfileDto profileDto = memberService.findMember(id);
        model.addAttribute("profileDto", profileDto);
        return "/members/edit";
    }

    /**
     * 프로필 수정
     * @param id
     * @param profileDto
     * @return ResponseEntity
     * @throws Exception
     */
    @PutMapping("/profile/update/{id}")
    public ResponseEntity updateProfile(@PathVariable Integer id,
                                @RequestBody ProfileDto profileDto) throws Exception {

        //비밀번호 암호화
        profileDto.setPassword(passwordEncoder.encode(profileDto.getPassword()));

        //프로필 업데이트
        profileService.updateProfile(profileDto);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 회원 정보 삭제
     * @param id
     * @param profileDto
     * @param request
     * @return ResponseEntity
     * @throws Exception
     */
    @DeleteMapping("/profile/delete/{id}")
    public ResponseEntity deleteProfile(@PathVariable Integer id,
                                           @RequestBody ProfileDto profileDto,
                                           HttpServletRequest request) throws Exception {

        //회원 정보 삭제
        profileService.deleteProfile(profileDto, request);

        //세션 초기화
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

}
