package com.doit.study.mapper;

import com.doit.study.file.domain.FileEntity;
import com.doit.study.file.dto.FileDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    //게시글 정보 넣기
    @Insert(FileSQL.insert)
    Integer insert(@Param("file") FileEntity fileEntity);

    //파일 아이디 가져오기
    @Select(FileSQL.findFile)
    FileDto findById(@Param("fileId") String fileId);

    //파일 찾기(by study_id)
    @Select(FileSQL.findByStudyId)
    List<FileDto> findByStudyId(@Param("study_id") Integer study_id);

    //스터디 아이디 업데이트
    @Update(FileSQL.updateStudyId)
    void updateStudyId(@Param("study_id") Integer study_id,
                       @Param("file_id") String file_id);


}
