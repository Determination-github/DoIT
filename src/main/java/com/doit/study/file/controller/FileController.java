package com.doit.study.file.controller;

import com.doit.study.file.dto.FileDto;
import com.doit.study.file.service.FileService;
import com.doit.study.file.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController {

    @Autowired
    ResourceLoader resourceLoader;

    private final FileService fileService;
    private final S3Uploader s3Uploader;

    /**
     * 게시글 이미지 파일 업로드
     * @param file
     * @return ResponseEntity
     * @throws Exception
     */
    @PostMapping("/file/images")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        FileDto fileDto = new FileDto();
        //S3에 파일 업로드
        s3Uploader.upload(file, "studyImageUpload", fileDto);

        return ResponseEntity.ok().body("/file/images/" +fileDto.getFile_id());
    }

    /**
     * 이미지 파일 가져오기
     * @param fileId
     * @param request
     * @return
     */
    @GetMapping("/file/images/{fileId}")
    public ResponseEntity serveFile(@PathVariable String fileId,
                                       HttpServletRequest request) throws Exception {

        //세션에 담긴 study_id 가져오기
        HttpSession session = request.getSession(false);
        Integer study_id = (Integer) session.getAttribute("study_id");

        FileDto fileDto = fileService.findFile(fileId);

        if(fileDto.getStudy_id() == null) {
            fileService.insertStudyId(study_id, fileId);
        }

        String path = fileDto.getFile_path();
        Resource resource = resourceLoader.getResource(path);
        return ResponseEntity.ok().body(resource);
    }

}
