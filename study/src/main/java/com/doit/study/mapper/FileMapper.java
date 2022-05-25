package com.doit.study.mapper;

import com.doit.study.file.domain.FileEntity;
import com.doit.study.file.dto.FileDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface FileMapper {

    //게시글 정보 넣기
    @Insert(FileSQL.insert)
    Integer insert(@Param("file") FileEntity fileEntity);


    @Select(FileSQL.findFile)
    FileDto findById(@Param("fileId") String fileId);
}
