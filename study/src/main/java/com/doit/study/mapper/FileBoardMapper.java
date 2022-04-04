package com.doit.study.mapper;

import com.doit.study.board.domain.FileBoard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface FileBoardMapper {

    //테이블 생성
    @Insert(FileBoardSQL.insert)
    Integer insert(@Param("fileBoard") FileBoard fileBoard);
}
