package com.doit.study.mapper;

//import com.doit.study.Board.domain.Board;
import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.SearchDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    //전체 글의 개수
    @Select(BoardSQL.count)
    Integer count();

    //페이징 처리한 후 게시글 정보 가져오기
    @Select(BoardSQL.selectPage)
    List<Board> selectPage(@Param("pagination") Pagination pagination);

    //글 삽입 후 글 정보 가져오기
    @Select(BoardSQL.getLastBoard)
    Board getLastBoard(@Param("id") Integer id);

    //글 삽입
    @Insert(BoardSQL.insertBoard)
    Integer insertStudyBoard(@Param("board") Board board);

    //조회수 증가
    @Update(BoardSQL.increaseViewCount)
    Integer increaseViewCount(@Param("study_id") int id);

    //id로 게시글 정보 찾기
    @Select(BoardSQL.getBoard)
    Optional<Board> findById(@Param("study_id") int study_id);

    //검색한 내용으로 게시글 개수 가져오기
    @Select(BoardSQL.getCountByKeyword)
    Integer getCountByKeyword(@Param("searchDto") SearchDto searchDto);


    @Select(BoardSQL.getMyStudyList)
    Integer getMyStudyList(@Param("user_id") String id);



}