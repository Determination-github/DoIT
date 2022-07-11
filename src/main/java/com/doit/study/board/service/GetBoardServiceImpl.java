package com.doit.study.board.service;

import com.doit.study.board.domain.Board;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GetBoardServiceImpl implements GetBoardService {

    private final BoardMapper boardMapper;
    private final MemberMapper memberMapper;

    /***
     * 전체 스터디 글 개수
     * @return Integer
     */
    @Override
    public Integer getCount() {
        return boardMapper.count();
    }

    /***
     * 작성한 글 개수 BY 회원아이디
     * @param id
     * @return Integer
     */
    @Override
    public Integer getCountById(Integer id) {
        return boardMapper.getCountById(id);
    }

    /***
     * 게시글 정보 가져오기
     * @param study_id
     * @param boardDto
     * @return BoardDto
     */
    @Override
    public BoardDto findResultById(Integer study_id, BoardDto boardDto) {

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
    public BoardDto findStudyById(Integer study_id) {
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
}
