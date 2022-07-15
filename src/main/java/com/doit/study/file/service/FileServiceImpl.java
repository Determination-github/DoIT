package com.doit.study.file.service;

import com.doit.study.file.dto.FileDto;
import com.doit.study.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService{

    private final FileMapper fileMapper;

    /***
     * 파일 찾기
     * @param fileId
     * @return FileDto
     */
    @Override
    public FileDto findFile(String fileId) {
        FileDto fileDto = fileMapper.findById(fileId);
        if(fileDto!=null) {
            return fileDto;
        }
        return null;
    }

    /***
     * 파일 목록 가져오기
     * @param study_id
     * @return List<FileDto>
     */
    @Override
    public List<FileDto> findFileByStudyId(Integer study_id) {

        List<FileDto> list = fileMapper.findByStudyId(study_id);

        return list;
    }

    /***
     * 파일 업데이트
     * @param study_id
     * @param file_id
     */
    @Override
    public void insertStudyId(Integer study_id, String file_id) {
        fileMapper.updateStudyId(study_id, file_id);
    }
}
