package com.doit.study.board.service;

import com.doit.study.board.domain.FileEntity;
import com.doit.study.board.dto.FileDto;
import com.doit.study.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final FileMapper fileMapper;

    //파일 저장 경로
    @Value("${file.dir}")
    private String fileDir;


    @Override
    public FileDto insertFile(String originalFileName) {

        String uuid = UUID.randomUUID().toString();
        FileDto fileDto = new FileDto(uuid, originalFileName, fileDir);
        FileEntity fileEntity = fileDto.toEntity(fileDto);
        Integer result = fileMapper.insert(fileEntity);

        if(result != null) {
            return fileDto;
        }
        return null;
    }

    @Override
    public String fileSave(FileDto fileDto, MultipartFile multipartFile) throws IOException {

        String saveFileName = fileDto.getFileId() + fileDto.getOriginalFileName();
        File insertFile = new File(fileDir, saveFileName);
        FileCopyUtils.copy(multipartFile.getBytes(), insertFile);

        return saveFileName;
    }

    @Override
    public FileDto findFile(String fileId) {
        FileDto fileDto = fileMapper.findById(fileId);
        if(fileDto!=null) {
            return fileDto;
        }
        return null;
    }
}
