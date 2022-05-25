package com.doit.study.mapper;

//import com.doit.study.Board.domain.Board;
import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.SearchBoardDto;
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


    @Select(BoardSQL.selectAll)
    public List<BoardDto> selectAll();

    @Delete(BoardSQL.deleteAll)
    int deleteAll();

    @Delete(BoardSQL.delete)
    int delete(BoardDto boardDto);

    @Insert(BoardSQL.insert)
    void insert(BoardDto boardDto);

    @Insert(BoardSQL.insertBoard)
    Integer insertStudyBoard(@Param("board") Board board);

    @Update(BoardSQL.increaseViewCount)
    Integer increaseViewCount(@Param("study_id") int id);

    @Select(BoardSQL.getBoard)
    Optional<Board> findById(@Param("study_id") int study_id);

//    @Update(BoardSQL.increaseViewCount)
//    public int increaseViewCount(Integer board_Id);

//    @Select(BoardSQL.selectPage)
//    public List<BoardDto> selectPage(Pagination pagination);



    @Select(BoardSQL.findNickname)
    String findNickname(@Param("user_id") String id);

    @Select(BoardSQL.select)
    BoardDto selectOne(Integer board_Id);

    @Select(BoardSQL.getMyStudyList)
    Integer getMyStudyList(@Param("user_id") String id);


    @Update(BoardSQL.update)
    int update(BoardDto boardDto);

//    @Select(BoardSQL.searchSelectPage)
//    List<SearchBoardDto> searchSelectPage(SearchBoardDto searchBoardDto);

    @Select(BoardSQL.searchResultCount)
    int searchResultCount(SearchBoardDto boardDto);

    @Update(BoardSQL.updateCommentCount)
    int updateCommentCount(@Param("study_id") String id, @Param("count") Integer count);

}