package com.doit.study.file.service;


import com.doit.study.file.dto.FileDto;

import java.util.List;

public interface FileService {

    //파일 찾기
    FileDto findFile(String fileId);

    //파일 목록 가져오기
    List<FileDto> findFileByStudyId(Integer study_id);

    //파일 업데이트
    void insertStudyId(Integer study_id, String file_id);
}
