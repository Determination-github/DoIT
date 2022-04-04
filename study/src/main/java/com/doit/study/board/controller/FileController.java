package com.doit.study.board.controller;

import com.doit.study.board.dto.FileDto;
import com.doit.study.board.service.BoardService;
import com.doit.study.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/file/images")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        FileDto fileDto = fileService.insertFile(originalFilename);
        String saveFileName = fileService.fileSave(fileDto, file);

        log.info("saveFileName={}", saveFileName);
        log.info("fileDto={}", fileDto);

        return ResponseEntity.ok().body("/file/images/" +fileDto.getFileId());

    }

    @GetMapping("/file/images/{fileId}")
    public ResponseEntity<?> serveFile(@PathVariable String fileId) {
        try {
            log.info("id = " + fileId);
            FileDto fileDto = fileService.findFile(fileId);
            String path = fileDto.getFilePath();
            String id = fileDto.getFileId();
            String name = fileDto.getOriginalFileName();
            Resource resource = resourceLoader.getResource("file:" + path+id+name);
            return ResponseEntity.ok().body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }






}
