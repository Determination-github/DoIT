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

//    //파일 저장 경로
//    @Value("${file.dir}")
//    private String fileDir;


//    @Override
//    public FileDto insertFile(String originalFileName) {
//
//        //고유 아이디 생성
//        String uuid = UUID.randomUUID().toString();
//
//        //fileDto 객체 생성 후 값 저장
//        FileDto fileDto = new FileDto(uuid, originalFileName, fileDir);
//
//        //DTO 객체 엔티티 객체로 변환
//        FileEntity fileEntity = fileDto.toEntity(fileDto);
//
//        //데이터 삽입
//        Integer result = fileMapper.insert(fileEntity);
//
//        if(result != null) {
//            return fileDto;
//        }
//        return null;
//    }
//
//    @Override
//    public String fileSave(FileDto fileDto, MultipartFile multipartFile) throws IOException {
//
//        //저장용 파일이름 설정
//        String saveFileName = fileDto.getFileId() + fileDto.getOriginalFileName();
//
//        //파일저장
//        File insertFile = new File(fileDir, saveFileName);
//
////        //디렉토리가 존재하지 않는 경우 디렉토리를 생성
//        if(!insertFile.exists()) {
//            boolean makeDir = insertFile.mkdirs();
//
//            //디텍토리 생성 실패시 오류 로그 출력
//            if(!makeDir) {
//                log.info("디렉토리 생성 실패");
//            }
//        }
//
//        FileCopyUtils.copy(multipartFile.getBytes(), insertFile);
//
//        return saveFileName;
//    }

    @Override
    public FileDto findFile(String fileId) {
        FileDto fileDto = fileMapper.findById(fileId);
        if(fileDto!=null) {
            return fileDto;
        }
        return null;
    }
}
