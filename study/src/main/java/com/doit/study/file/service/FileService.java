package com.doit.study.file.service;


import com.doit.study.file.dto.FileDto;

import java.util.List;

public interface FileService {

    FileDto findFile(String fileId);

    List<FileDto> findFileByStudyId(Integer study_id);

    void insertStudyId(Integer study_id, String file_id);
}
