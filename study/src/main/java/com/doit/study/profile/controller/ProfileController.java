package com.doit.study.profile.controller;

import com.doit.study.file.dto.FileDto;
import com.doit.study.board.service.BoardService;
import com.doit.study.file.service.FileService;
import com.doit.study.file.service.S3Uploader;
import com.doit.study.profile.dto.ProfileDto;
import com.doit.study.member.service.MemberService;
import com.doit.study.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    ResourceLoader resourceLoader;

    private final MemberService memberService;
    private final ProfileService profileService;
    private final S3Uploader s3Uploader;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Integer id, Model model) {
        ProfileDto profileDto = new ProfileDto();
        profileDto = memberService.findMember(id);
//        int size = boardService.getCountMyStudy(id);
//        profileDto.setSize(size);
        log.info("profiledto={}",profileDto);
        String path = profileService.findImage(id);
        profileDto.setFile_path(path);
        model.addAttribute("profileDto", profileDto);
        return "/members/profile";
    }

    @PostMapping("/profile/{id}")
    @ResponseBody
    public ResponseEntity<?> uploadProfile(@RequestParam("file") MultipartFile file,
                                           @PathVariable Integer id) throws IOException {
        log.info("MultipartFile={}", file);

        ProfileDto profileDto = new ProfileDto();
        s3Uploader.profileUpload(file, "profileImageUpload", profileDto, id);
        log.info("profileDto는 ? " + profileDto);

        String fileId = profileDto.getFile_id();

        try {
            log.info("id = " + fileId);
            profileDto = profileService.findFile(fileId);
            log.info("path = " + profileDto.getFile_path());
            Map<String, String> path = new HashMap<>();
            path.put("path", profileDto.getFile_path());
//            String id = fileDto.getFile_id();
//            String name = fileDto.getFile_origin_name();
            return ResponseEntity.ok().body(path);
        } catch (Exception e) {
            log.info("실패");
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/profile/edit/{id}")
    public String editProfile(@PathVariable Integer id,
                              Model model) {
        log.info("id={}", id);
        ProfileDto profileDto = memberService.findMember(id);
        log.info("profileDto={}", profileDto);

        model.addAttribute("profileDto", profileDto);
        return "/members/edit";
    }

    @PutMapping("/profile/update/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer id,
                                @RequestBody ProfileDto profileDto) {
        log.info("id={}", id);

        profileService.updateProfile(profileDto);
        return ResponseEntity.ok().body(id);
    }

}
