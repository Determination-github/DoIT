package com.doit.study.board.service;

import com.doit.study.board.domain.Board;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.mapper.*;
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

}