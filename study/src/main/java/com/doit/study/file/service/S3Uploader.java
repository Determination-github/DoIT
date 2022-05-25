package com.doit.study.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.doit.study.file.domain.FileEntity;
import com.doit.study.file.dto.FileDto;
import com.doit.study.mapper.FileMapper;
import com.doit.study.mapper.ProfileMapper;
import com.doit.study.profile.domain.Profile;
import com.doit.study.profile.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final FileMapper fileMapper;
    private final ProfileMapper profileMapper;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public String upload(MultipartFile multipartFile, String dirName, FileDto fileDto) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        return upload(uploadFile, dirName, fileDto);
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName, FileDto fileDto) {
        String fileId = String.valueOf(UUID.randomUUID());
        String fileName = dirName + "/" + fileId + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        fileDto.setFile_origin_name(uploadFile.getName());
        fileDto.setFile_id(fileId);
        fileDto.setFile_path(uploadImageUrl);

        FileEntity fileEntity = fileDto.toEntity(fileDto);

        //DB 저장
        Integer result = fileMapper.insert(fileEntity);

        if(result != null) {
            removeNewFile(uploadFile);
            return uploadImageUrl;
        }

        return null;
    }

    public String profileUpload(MultipartFile multipartFile, String dirName, ProfileDto profileDto, Integer id) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        return profileUpload(uploadFile, dirName, profileDto, id);
    }

    // S3로 파일 업로드하기
    private String profileUpload(File uploadFile, String dirName, ProfileDto profileDto, Integer id) {
        String fileId = String.valueOf(UUID.randomUUID());
        String fileName = dirName + "/" + fileId + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        profileDto.setFile_origin_name(uploadFile.getName());
        profileDto.setFile_id(fileId);
        profileDto.setFile_path(uploadImageUrl);
        profileDto.setId(id);

        Profile profile = profileDto.toEntity(profileDto);
        log.info("id={}, fileId={}, fileName={}, path={}", profile.getId(), profile.getFile_id(), profile.getFile_origin_name(), profile.getFile_path());

        //기존 프로필 삭제
        profileMapper.deleteProfile(id);

        //DB 저장
        Integer result = profileMapper.insert(profile);
        log.info("profile db 저장");

        if(result != null) {
            removeNewFile(uploadFile);
            return uploadImageUrl;
        }

        return null;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
}
