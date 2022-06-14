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
        List<Board> boardList = boardMapper.selectPage(pagination);

        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardList) {
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
     * 전체 스터디 글 가져오기
     * @param id
     * @param pagination
     * @return List<BoardDto>
     */
    @Override
    public List<BoardDto> getStudyBoardListAll(Integer id, Pagination pagination) {
        List<Board> boardList = boardMapper.selectPageAll(pagination);

        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardList) {
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
        List<Board> boardList = boardMapper.selectWishPageAll(wishlist,pagination);

        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardList) {
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

        Board board = boardDto.toEntity(boardDto);
        log.info("Board={}", board);
        Integer result = boardMapper.insertStudyBoard(board);
        Integer id = board.getId();
        board = boardMapper.getLastBoard(id);
        log.info("result={}", result);

        if (result != null) {
            return board.getStudy_id();
        }
        return null;
    }

    /***
     * 게시글 정보 가져오기
     * @param study_id
     * @param boardDto
     * @return BoardDto
     */
    @Override
    public BoardDto findResultById(int study_id, BoardDto boardDto) {
        log.info("study_id={}", study_id);
        Optional<Board> findBoard = boardMapper.findById(study_id);
        if(findBoard.isPresent()) {
            Board board = findBoard.get();
            boardDto.setBoard_id(board.getStudy_id());
            boardDto.setBoard_writerId(board.getId());
            boardDto.setBoard_regDate(board.getReg_date());
            log.info("boardDto={}", boardDto);
            return boardDto;
        }

        return null;
    }

    /***
     * 게시글 정보 가져오기(BoardDto 값이 없을 때)
     * @param study_id
     * @return BoardDto
     */
    @Override
    public BoardDto findStudyById(int study_id) {
        log.info("study_id={}", study_id);
        boardMapper.increaseViewCount(study_id);
        Optional<Board> findBoard = boardMapper.findById(study_id);
        if(findBoard.isPresent()) {
            Board board = findBoard.get();
            log.info("boardId={}",board.getStudy_id());
            BoardDto boardDto = new BoardDto().toBoardDto(board);
            int userId = boardDto.getBoard_writerId();
            String nickname = memberMapper.findNickname(userId);
            boardDto.setWriter_nickName(nickname);
            log.info("boardDto={}", boardDto);
            return boardDto;
        }
        return null;
    }

    /***
     * 게시글 수정
     * @param boardDto
     * @return BoardDto
     * @throws Exception
     */
    @Override
    public BoardDto updateBoard(BoardDto boardDto) throws Exception {
        log.info("update boardDto={}", boardDto);

        Board board = boardDto.toEntity(boardDto);
        boardMapper.updateBoard(board);
        Integer id = board.getStudy_id();
        Optional<Board> getBoard = boardMapper.findById(id);
        if(getBoard.isPresent()){
            Board updateBoard = getBoard.get();
            return boardDto.toBoardDto(updateBoard);
        }
        return null;
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

}