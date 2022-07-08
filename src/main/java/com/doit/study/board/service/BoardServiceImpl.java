package com.doit.study.board.service;

import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.SearchDto;
import com.doit.study.mapper.*;
import com.doit.study.wishlist.dto.WishlistDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final MemberMapper memberMapper;
    private final ProfileMapper profileMapper;
    private final CommentMapper commentMapper;
    private final WishListMapper wishListMapper;


    /***
     * 전체 스터디 글 개수
     * @return Integer
     */
    @Override
    public Integer getCount() {
        return boardMapper.count();
    }

    /**
     * 모집 중인 전체 스터디 글 가져오기
     * @param id
     * @param pagination
     * @return List<BoardDto>
     */
    @Override
    public List<BoardDto> getStudyBoardList(Integer id, Pagination pagination) {
        //boardlist 가져오기
        List<Board> boardList = boardMapper.selectPage(pagination);

        //boardlist를 담을 board dto 리스트 객체 생성
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardList) {
            //entity to dto
            BoardDto boardDto = new BoardDto().toBoardDto(board);

            //닉네임 가져오기
            String nickName = memberMapper.findNickname(board.getId());
            boardDto.setWriter_nickName(nickName);

            //프로필 사진 가져오기
            String path = profileMapper.getImagePath(board.getId());
            if(path != null) {
                boardDto.setPath(path);
            }

            //댓글 수 가져오기
            int count = commentMapper.count(boardDto.getBoard_id());
            boardDto.setBoard_commentCount(count);

            //좋아요 여부 확인
            Integer check = wishListMapper.getWishlistCount(id, boardDto.getBoard_id());
            if(check != 0) {
                boardDto.setBoard_like(true);
            } else {
                boardDto.setBoard_like(false);
            }

            //boardDto에 담기
            boardDtos.add(boardDto);
        }

        return boardDtos;
    }

    /**
     * 회원 게시글 가져오기
     * @param id
     * @param pagination
     * @return List<BoardDto>
     */
    @Override
    public List<BoardDto> getMyStudyBoardList(Integer id, Pagination pagination) {
        //boardlist 가져오기
        List<Board> boardList = boardMapper.selectPageAll(id, pagination);

        //boardlist를 담을 board dto 리스트 객체 생성
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardList) {
            //entity to dto
            BoardDto boardDto = new BoardDto().toBoardDto(board);

            //닉네임 가져오기
            String nickName = memberMapper.findNickname(board.getId());
            boardDto.setWriter_nickName(nickName);

            //프로필 사진 가져오기
            String path = profileMapper.getImagePath(board.getId());
            if(path != null) {
                boardDto.setPath(path);
            }

            //댓글 수 가져오기
            int count = commentMapper.count(boardDto.getBoard_id());
            boardDto.setBoard_commentCount(count);

            //좋아요 여부 확인
            Integer check = wishListMapper.getWishlistCount(id, boardDto.getBoard_id());
            if(check != 0) {
                boardDto.setBoard_like(true);
            } else {
                boardDto.setBoard_like(false);
            }

            boardDtos.add(boardDto);
        }
        return boardDtos;
    }

    /**
     * 위시리스트에 담긴 글 가져오기
     * @param id
     * @param wishlist
     * @param pagination
     * @return List<BoardDto>
     */
    @Override
    public List<BoardDto> getWishlistBoardListAll(Integer id, List<WishlistDto> wishlist, Pagination pagination) {
        //boardlist 가져오기
        List<Board> boardList = boardMapper.selectWishPageAll(wishlist,pagination);

        //boardlist를 담을 board dto 리스트 객체 생성
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardList) {
            //entity to dto
            BoardDto boardDto = new BoardDto().toBoardDto(board);

            //닉네임 가져오기
            String nickName = memberMapper.findNickname(board.getId());
            boardDto.setWriter_nickName(nickName);

            //프로필 사진 가져오기
            String path = profileMapper.getImagePath(board.getId());
            if(path != null) {
                boardDto.setPath(path);
            }

            //댓글 수 가져오기
            int count = commentMapper.count(boardDto.getBoard_id());
            boardDto.setBoard_commentCount(count);

            //좋아요 여부 확인
            Integer check = wishListMapper.getWishlistCount(id, boardDto.getBoard_id());
            if(check != 0) {
                boardDto.setBoard_like(true);
            } else {
                boardDto.setBoard_like(false);
            }

            boardDtos.add(boardDto);
        }
        return boardDtos;
    }

    /***
     * 글 추가하기
     * @param boardDto
     * @return Integer
     * @throws Exception
     */
    @Override
    public Integer insertStudyBoard(BoardDto boardDto) throws Exception {

        //dto to entity
        Board board = boardDto.toEntity(boardDto);

        //글 삽입
        Integer result = boardMapper.insertStudyBoard(board);

        //아이디 가져오기
        Integer id = board.getId();

        //마지막 삽입된 board정보 가져오기
        board = boardMapper.getLastBoard(id);

        if (result != null) {
            return board.getStudy_id();
        } else {
            return null;
        }
    }

    /***
     * 게시글 정보 가져오기
     * @param study_id
     * @param boardDto
     * @return BoardDto
     */
    @Override
    public BoardDto findResultById(int study_id, BoardDto boardDto) {

        //게시글 정보 가져오기
        Optional<Board> findBoard = boardMapper.findById(study_id);

        if(findBoard.isPresent()) { //게시글 정보가 있다면
            Board board = findBoard.get();

            //boardDto 정보 세팅
            boardDto.setBoard_id(board.getStudy_id());
            boardDto.setBoard_writerId(board.getId());
            boardDto.setBoard_regDate(board.getReg_date());
            return boardDto;
        } else {
            return null;
        }
    }

    /***
     * 게시글 정보 가져오기(BoardDto 값이 없을 때)
     * @param study_id
     * @return BoardDto
     */
    @Override
    public BoardDto findStudyById(int study_id) {
        //게시글 확인시 조회수 증가
        boardMapper.increaseViewCount(study_id);

        //게시글 정보 가져오기
        Optional<Board> findBoard = boardMapper.findById(study_id);

        if(findBoard.isPresent()) { //게시글 정보가 있다면
            //게시글 정보 가져오기
            Board board = findBoard.get();

            //entity to dto
            BoardDto boardDto = new BoardDto().toBoardDto(board);

            //boardDto 추가 정보 설정
            int userId = boardDto.getBoard_writerId();
            String nickname = memberMapper.findNickname(userId);
            boardDto.setWriter_nickName(nickname);

            return boardDto;
        } else {
            return null;
        }
    }

    /***
     * 게시글 수정
     * @param boardDto
     * @return BoardDto
     * @throws Exception
     */
    @Override
    public BoardDto updateBoard(BoardDto boardDto) throws Exception {

        //dto to entity
        Board board = boardDto.toEntity(boardDto);

        //게시글 업데이트
        boardMapper.updateBoard(board);

        Integer id = board.getStudy_id();
        Optional<Board> getBoard = boardMapper.findById(id);
        if(getBoard.isPresent()){ //가져온 게시글 정보가 있다면
            Board updateBoard = getBoard.get();
            return boardDto.toBoardDto(updateBoard);
        } else {
            return null;
        }
    }

    /***
     * 게시글 삭제
     * @param study_id
     * @return Integer
     * @throws Exception
     */
    @Override
    public Integer deleteBoard(int study_id) throws Exception {
        return boardMapper.deleteBoard(study_id);
    }

    /***
     * 작성한 글 개수 BY 회원아이디
     * @param id
     * @return Integer
     */
    @Override
    public Integer getCountById(int id) {
        return boardMapper.getCountById(id);
    }

    /***
     * 검색 내용에 따른 게시글 개수
     * @param searchDto
     * @return Integer
     */
    @Override
    public Integer getCountBySearching(SearchDto searchDto) {
        return boardMapper.getCountByKeyword(searchDto);
    }

    /**
     * 검색한 스터디 글 가져오기
     * @param id
     * @param searchDto
     * @return List<BoardDto>
     */
    @Override
    public List<BoardDto> getSearchStudyBoardList(Integer id, SearchDto searchDto, Pagination pagination) {
        //boardlist 가져오기
        List<Board> boardList = boardMapper.selectSearchPage(searchDto, pagination);

        //boardlist를 담을 board dto 리스트 객체 생성
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardList) {
            //entity to dto
            BoardDto boardDto = new BoardDto().toBoardDto(board);

            //닉네임 가져오기
            String nickName = memberMapper.findNickname(board.getId());
            boardDto.setWriter_nickName(nickName);

            //프로필 사진 가져오기
            String path = profileMapper.getImagePath(board.getId());
            if(path != null) {
                boardDto.setPath(path);
            }

            //댓글 수 가져오기
            int count = commentMapper.count(boardDto.getBoard_id());
            boardDto.setBoard_commentCount(count);

            //좋아요 여부 확인
            Integer check = wishListMapper.getWishlistCount(id, boardDto.getBoard_id());
            if(check != 0) {
                boardDto.setBoard_like(true);
            } else {
                boardDto.setBoard_like(false);
            }

            //boardDto에 담기
            boardDtos.add(boardDto);
        }

        return boardDtos;
    }
}