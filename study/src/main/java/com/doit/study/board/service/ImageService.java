package com.doit.study.board.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ImageService {
    //파일 저장 경로
    @Value("${file.dir}")
    private String fileDir;

//    public FileDto store(MultipartFile file) throws Exception {
//        //		 fileName : 예류2.jpg
//        //		 filePath : d:/images/uuid-예류2.jpg
//        //		 saveFileName : uuid-예류2.png
//        //		 contentType : image/jpeg
//        //		 size : 4994942
//        //		 registerDate : 2020-02-06 22:29:57.748
//        try {
//            if(file.isEmpty()) {
//                throw new Exception("Failed to store empty file " + file.getOriginalFilename());
//            }
//
////            String saveFileName = fileSave(fileDir, file);
////            FileDto saveFile = new FileDto();
////            saveFile.setFileName(file.getOriginalFilename());
////            saveFile.setSaveFileName(saveFileName);
////            saveFile.setFilePath(rootLocation.toString().replace(File.separatorChar, '/') +'/' + saveFileName);
////            uploadFileRepository.save(saveFile);
////            return saveFile;
//
//        } catch(IOException e) {
//            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
//        }
//
//
//    }
////
//    public UploadFile load(Long fileId) {
//        return uploadFileRepository.findById(fileId).get();
//    }

    public String fileSave(String rootLocation, MultipartFile file) throws IOException {
        File uploadDir = new File(rootLocation);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // saveFileName 생성
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid.toString() + file.getOriginalFilename();
        File saveFile = new File(rootLocation, saveFileName);
        FileCopyUtils.copy(file.getBytes(), saveFile);

        return saveFileName;
    }


}
