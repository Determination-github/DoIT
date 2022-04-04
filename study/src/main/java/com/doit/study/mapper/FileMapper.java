package com.doit.study.mapper;

import com.doit.study.board.domain.FileEntity;
import com.doit.study.board.dto.FileDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface FileMapper {

    //테이블 생성
    @Insert(FileSQL.insert)
    Integer insert(@Param("file") FileEntity fileEntity);


    @Select(FileSQL.findFile)
    FileDto findById(@Param("fileId") String fileId);
}
