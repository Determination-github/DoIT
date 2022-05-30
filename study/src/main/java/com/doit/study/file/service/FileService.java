package com.doit.study.file.service;


import com.doit.study.file.dto.FileDto;

public interface FileService {

    FileDto findFile(String fileId);

    void insertStudyId(Integer study_id, String file_id);
}
