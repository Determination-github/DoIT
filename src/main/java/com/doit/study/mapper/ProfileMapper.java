package com.doit.study.mapper;

import com.doit.study.profile.domain.Profile;
import com.doit.study.profile.dto.ProfileDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProfileMapper {

    //게시글 정보 넣기
    @Insert(ProfileSQL.insert)
    Integer insert(@Param("file") Profile profile);

    @Select(ProfileSQL.findFile)
    ProfileDto findById(@Param("fileId") String fileId);

    @Select(ProfileSQL.findImagePath)
    String getImagePath(@Param("id") Integer id);

    @Delete(ProfileSQL.deleteProfile)
    void deleteProfile(@Param("id") Integer id);
}
