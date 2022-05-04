package com.doit.study.board.service;

import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.mapper.BoardMapper;
import com.doit.study.mapper.MemberMapper;
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

//    @Override
//    public List<BoardDto> getList() {
//
//        List<BoardDto> boardList = boardMapper.selectAll();
//        return boardList;
//    }

    @Override
    public Integer getCount() {
        return boardMapper.count();
    }

    @Override
    public Integer getCountMyStudy(String id) {
        return boardMapper.getMyStudyList(id);
    }


//    @Override
//    public BoardDto read(Integer board_Id) throws Exception {
//        BoardDto boardDto = boardMapper.selectOne(board_Id);
//        boardMapper.increaseViewCount(board_Id);
//
//        return boardDto;
//    }

//    @Override
//    public void write(BoardDto boardWriteDto) {
//        boardMapper.insert(boardDto);
//    }

//    @Override
//    public int modify(BoardDto boardWriteDto) {
//        return boardMapper.update(boardDto);
//    }

//    @Override
//    public int remove(BoardDto boardDto)  {
//        return boardMapper.delete(boardDto);
//    }
//
//    @Override
//    public int remove(String board_Writer) {
//        return boardMapper.delete(board_Writer);
//    }

//    @Override
//    public int searchResultCount(SearchBoardDto searchBoardDto){
//        return boardMapper.searchResultCount(searchBoardDto);
//    }

//    @Override
//    public List<SearchBoardDto> searchSelectPage(SearchBoardDto searchBoardDto) {
//        return boardMapper.searchSelectPage(searchBoardDto);
//    }

//    @Override
//    public int updateCommentCount(String board_Id, int count) {
//        return boardMapper.updateCommentCount(board_Id, count);
//    }

//    @Override
//    public Integer getBoardCount() {
//        return boardMapper.count();
//    }

//    @Override
//    public List<BoardDto> getPage(Pagination pagination) throws Exception {
//        log.info("BoardDto = " + boardMapper.selectPage(pagination));
//        return boardMapper.selectPage(pagination);
//    }

    @Override
    public List<BoardDto> getStudyBoardList(Pagination pagination) {
        List<Board> boardList = boardMapper.selectPage(pagination);

        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardList) {
            BoardDto boardDto = new BoardDto().toBoardDto(board);

            //닉네임 가져오기
            String nickName = memberMapper.findNickname(board.getId());
            boardDto.setWriter_nickName(nickName);

//            boardDto.setWriter_nickName(nickName);
//            boardDto.setBoard_writerId(board.getUser_id());
//            boardDto.setBoard_id(board.getStudy_id());
//            boardDto.setBoard_title(board.getTitle());
//            boardDto.setBoard_subTitle(board.getSub_title());
//            boardDto.setBoard_location(board.getAddress());
//            boardDto.setBoard_startDate(board.getSchedule_start());
//            boardDto.setBoard_endDate(board.getSchedule_end());
//            boardDto.setBoard_regDate(board.getReg_date());
//            boardDto.setWriter_interest1(board.getInterest1());
//            boardDto.setWriter_interest2(board.getInterest2());
//            boardDto.setWriter_interest3(board.getInterest3());
//            boardDto.setBoard_onOffline(board.getMoim_flag());
//            boardDto.setBoard_commentCount(board.getComment_count());
//            boardDto.setBoard_viewCount(board.getView_count());

            log.info("boardWriteDto={}", boardDto);

            boardDtos.add(boardDto);
        }

        return boardDtos;
    }



    @Override
    public Integer insertStudyBoard(BoardDto boardDto) {

        Board board = boardDto.toEntity(boardDto);
        log.info("Board={}", board);
        Integer result = boardMapper.insertStudyBoard(board);
        log.info("result={}", result);

        if (result != null) {
            return board.getStudy_id();
        }
        return null;
    }

    @Override
    public BoardDto findResultById(String study_id, BoardDto boardDto) {
        log.info("study_id={}", study_id);
        Optional<Board> findBoard = boardMapper.findById(study_id);
        if(findBoard.isPresent()) {
            Board board = findBoard.get();
            boardDto.setBoard_id(board.getStudy_id());
            boardDto.setBoard_writerId(board.getId());
            boardDto.setBoard_commentCount(board.getComment_count());
            boardDto.setBoard_viewCount(board.getView_count());
            boardDto.setBoard_regDate(board.getReg_date());
            log.info("boardDto={}", boardDto);
            return boardDto;
        }

        return null;
    }

    @Override
    public BoardDto findStudyById(String study_id) {
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
            log.info("boardWriteDto={}", boardDto);
            return boardDto;
        }
        return null;
    }

//    @Override
//    public void increasViewCount(String id) {
//        boardMapper.increaseViewCount(id);
//    }

//    @Override
//    public int searchResultCount() throws Exception {
//        return boardMapper.searchResultCount();
//    }
//
//    @Override
//    public List<BoardDto> searchSelectPage(Pagination pagination) throws Exception {
//        return boardMapper.searchSelectPage(pagination);

//    @Override
//    public int searchResultCount(SearchCondition sc) throws Exception {
//        return boardMapper.searchResultCount(sc);
//    }
//
//    @Override
//    public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception {
//        return boardMapper.searchSelectPage(sc);
//    }


}