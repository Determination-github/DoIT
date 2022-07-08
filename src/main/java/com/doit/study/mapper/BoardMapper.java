package com.doit.study.mapper;

import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.SearchDto;
import com.doit.study.wishlist.dto.WishlistDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    //모집 중인 전체 글의 개수
    @Select(BoardSQL.count)
    Integer count();

    //페이징 처리한 후 게시글 정보 가져오기
    @Select(BoardSQL.selectPage)
    List<Board> selectPage(@Param("pagination") Pagination pagination);

    //페이징 처리한 후 게시글 정보 모두 가져오기
    @Select(BoardSQL.selectPageById)
    List<Board> selectPageAll(@Param("id") Integer id,
                              @Param("pagination") Pagination pagination);

    //페이징 처리한 후 게시글 정보 모두 가져오기
    @Select(BoardSQL.selectWishPageAll)
    List<Board> selectWishPageAll(@Param("wishlist") List<WishlistDto> wishlistDto,
                                  @Param("pagination") Pagination pagination);

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

    //검색한 온라인 스터디 내용으로 게시글 개수 가져오기
    @Select(BoardSQL.getOnlineBoardCountByKeyword)
    Integer getOnlineBoardCountByKeyword(@Param("searchDto") SearchDto searchDto);

    //검색한 오프라인 스터디 내용으로 게시글 개수 가져오기
    @Select(BoardSQL.getOfflineBoardCountByKeyword)
    Integer getOfflineBoardCountByKeyword(@Param("searchDto") SearchDto searchDto);

    //게시글 업데이트
    @Update(BoardSQL.updateBoard)
    void updateBoard(@Param("board") Board board);

    //게시글 삭제
    @Delete(BoardSQL.deleteBoard)
    Integer deleteBoard(@Param("study_id") int study_id);

    //게시글 개수 조회 by id
    @Select(BoardSQL.getCountById)
    Integer getCountById(int id);

    //검색어로 오프라인 스터디 글 가져오기
    @Select(BoardSQL.getOfflineBoardByKeyword)
    List<Board> selectOfflineSearchPage(@Param("searchDto") SearchDto searchDto,
                                 @Param("pagination") Pagination pagination);

    //검색어로 온라인 스터디 글 가져오기
    @Select(BoardSQL.getOnlineBoardByKeyword)
    List<Board> selectOnlineSearchPage(@Param("searchDto") SearchDto searchDto,
                                 @Param("pagination") Pagination pagination);

    @Select(BoardSQL.getMyStudyList)
    Integer getMyStudyList(@Param("user_id") String id);


}