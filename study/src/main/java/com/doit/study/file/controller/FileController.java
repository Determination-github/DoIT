package com.doit.study.file.controller;

import com.doit.study.file.dto.FileDto;
import com.doit.study.board.service.BoardService;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController {

    @Autowired
    ResourceLoader resourceLoader;

    private final FileService fileService;
    private final S3Uploader s3Uploader;

    @PostMapping("/file/images")
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        FileDto fileDto = new FileDto();
        s3Uploader.upload(file, "studyImageUpload", fileDto);
        log.info("fileDto는 ? " + fileDto);

        return ResponseEntity.ok().body("/file/images/" +fileDto.getFile_id());
    }

    @GetMapping("/file/images/{fileId}")
    public ResponseEntity<?> serveFile(@PathVariable String fileId,
                                       HttpServletRequest request) {
        try {
            log.info("id = " + fileId);

            //세션에 담긴 study_id 가져오기
            HttpSession session = request.getSession(false);
            Integer study_id = (Integer) session.getAttribute("study_id");
            log.info("study_id = "+study_id);

            FileDto fileDto = fileService.findFile(fileId);
            log.info("fileDto = " + fileDto);
            if(fileDto.getStudy_id() == null) {
                fileService.insertStudyId(study_id, fileId);
            }

            String path = fileDto.getFile_path();
            Resource resource = resourceLoader.getResource(path);
            return ResponseEntity.ok().body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }






}
