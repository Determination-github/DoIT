package com.doit.study.board.service;

import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.SearchDto;
import com.doit.study.mapper.BoardMapper;
import com.doit.study.mapper.CommentMapper;
import com.doit.study.mapper.MemberMapper;
import com.doit.study.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final MemberMapper memberMapper;
    private final ProfileMapper profileMapper;
    private final CommentMapper commentMapper;


    //전체 스터디 글 개수
    @Override
    public Integer getCount() {
        return boardMapper.count();
    }

    //전체 스터디 글 가져오기
    @Override
    public List<BoardDto> getStudyBoardList(Pagination pagination) {
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

            log.info("boardWriteDto={}", boardDto);

            boardDtos.add(boardDto);
        }

        return boardDtos;
    }


    //글 추가하기
    @Override
    public Integer insertStudyBoard(BoardDto boardDto) {

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

    //게시글 정보 가져오기
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

    //게시글 정보 가져오기(BoardDto 값이 없을 때)
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

    @Override
    public BoardDto updateBoard(BoardDto boardDto) {
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

    //검색 내용에 따른 게시글 개수
    @Override
    public Integer getCountBySearching(SearchDto searchDto) {
        return boardMapper.getCountByKeyword(searchDto);
    }



//
//    @Override
//    public Integer getCountMyStudy(String id) {
//        return boardMapper.getMyStudyList(id);
//    }

}