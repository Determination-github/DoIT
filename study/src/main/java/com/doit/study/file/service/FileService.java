package com.doit.study.file.service;


import com.doit.study.file.dto.FileDto;

public interface FileService {

//    FileDto insertFile(String originalFileName);
//
//    String fileSave(FileDto fileDto, MultipartFile multipartFile) throws IOException;

    FileDto findFile(String fileId);

}
