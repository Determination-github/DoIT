package com.doit.study.board.controller;

import com.doit.study.board.dto.FileDto;
import com.doit.study.board.service.BoardService;
import com.doit.study.board.service.FileService;
import com.doit.study.board.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController {

    @Autowired
    ResourceLoader resourceLoader;

    private final FileService fileService;
    private final BoardService boardService;
    private final S3Uploader s3Uploader;

//    @PostMapping("/file/images")
//    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
//        String originalFilename = file.getOriginalFilename();
//        FileDto fileDto = fileService.insertFile(originalFilename);
//        String saveFileName = fileService.fileSave(fileDto, file);
//
//        log.info("saveFileName={}", saveFileName);
//        log.info("fileDto={}", fileDto);
//
//        return ResponseEntity.ok().body("/file/images/" +fileDto.getFileId());
//    }

    @PostMapping("/file/images")
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        FileDto fileDto = new FileDto();
        s3Uploader.upload(file, "studyImageUpload", fileDto);
        log.info("fileDto는 ? " + fileDto);

        return ResponseEntity.ok().body("/file/images/" +fileDto.getFile_id());
    }

    @GetMapping("/file/images/{fileId}")
    public ResponseEntity<?> serveFile(@PathVariable String fileId) {
        try {
            log.info("id = " + fileId);
            FileDto fileDto = fileService.findFile(fileId);
            String path = fileDto.getFile_path();
            String id = fileDto.getFile_id();
            String name = fileDto.getFile_origin_name();
            Resource resource = resourceLoader.getResource(path);
//            Resource resource = new ClassPathResource(path);
            return ResponseEntity.ok().body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }






}
