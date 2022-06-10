package com.doit.study.file.service;

import com.doit.study.file.dto.FileDto;
import com.doit.study.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final FileMapper fileMapper;

    @Override
    public FileDto findFile(String fileId) {
        FileDto fileDto = fileMapper.findById(fileId);
        if(fileDto!=null) {
            return fileDto;
        }
        return null;
    }

    @Override
    public List<FileDto> findFileByStudyId(Integer study_id) {

        List<FileDto> list = fileMapper.findByStudyId(study_id);

        return list;
    }

    @Override
    public void insertStudyId(Integer study_id, String file_id) {
        fileMapper.updateStudyId(study_id, file_id);
    }
}
