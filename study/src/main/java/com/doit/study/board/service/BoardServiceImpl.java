package com.doit.study.board.service;

import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.BoardWriteDto;
import com.doit.study.board.dto.SearchBoardDto;
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

    @Override
    public List<BoardDto> getList() throws Exception {

        List<BoardDto> boardList = boardMapper.selectAll();
        return boardList;
    }

    @Override
    public int getCount() throws Exception {
        return boardMapper.count();
    }


//    @Override
//    public BoardDto read(Integer board_Id) throws Exception {
//        BoardDto boardDto = boardMapper.selectOne(board_Id);
//        boardMapper.increaseViewCount(board_Id);
//
//        return boardDto;
//    }

    @Override
    public void write(BoardDto boardDto) throws Exception {
        boardMapper.insert(boardDto);
    }

    @Override
    public int modify(BoardDto boardDto) throws Exception {
        return boardMapper.update(boardDto);
    }

    @Override
    public int remove(BoardDto boardDto) throws Exception {
        return boardMapper.delete(boardDto);
    }

    @Override
    public int remove(String board_Writer) throws Exception {
        return boardMapper.delete(board_Writer);
    }

    @Override
    public int searchResultCount(SearchBoardDto searchBoardDto) throws Exception {
        return boardMapper.searchResultCount(searchBoardDto);
    }

    @Override
    public List<SearchBoardDto> searchSelectPage(SearchBoardDto searchBoardDto) throws Exception {
        return boardMapper.searchSelectPage(searchBoardDto);
    }

    @Override
    public Integer getBoardCount() {
        return boardMapper.count();
    }

//    @Override
//    public List<BoardDto> getPage(Pagination pagination) throws Exception {
//        log.info("BoardDto = " + boardMapper.selectPage(pagination));
//        return boardMapper.selectPage(pagination);
//    }

    @Override
    public List<BoardWriteDto> getStudyBoardList(Pagination pagination) {
        List<Board> boardList = boardMapper.selectPage(pagination);

        List<BoardWriteDto> boardWriteDtos = new ArrayList<>();
        for (Board board : boardList) {
            BoardWriteDto boardWriteDto = new BoardWriteDto();
            String nickName = memberMapper.nickname(board.getUser_id());
            boardWriteDto.setWriter_nickName(nickName);
            boardWriteDto.setBoard_writerId(board.getUser_id());
            boardWriteDto.setBoard_id(board.getStudy_id());
            boardWriteDto.setBoard_title(board.getTitle());
            boardWriteDto.setBoard_subTitle(board.getSub_title());
            boardWriteDto.setBoard_location(board.getAddress());
            boardWriteDto.setBoard_startDate(board.getSchedule_start());
            boardWriteDto.setBoard_endDate(board.getSchedule_end());
            boardWriteDto.setBoard_regDate(board.getReg_date());
            boardWriteDto.setWriter_interest1(board.getInterest1());
            boardWriteDto.setWriter_interest2(board.getInterest2());
            boardWriteDto.setWriter_interest3(board.getInterest3());
            boardWriteDto.setBoard_onOffline(board.getMoim_flag());
            boardWriteDto.setBoard_commentCount(board.getComment_count());
            boardWriteDto.setBoard_viewCount(board.getView_count());

            log.info("boardWriteDto={}", boardWriteDto);

            boardWriteDtos.add(boardWriteDto);
        }

        return boardWriteDtos;
    }



    @Override
    public String insertStudyBoard(BoardWriteDto boardWriteDto) {

        String uuid = UUID.randomUUID().toString();
        Board board = boardWriteDto.toEntity(uuid, boardWriteDto);
        log.info("Board={}", board);
        Integer result = boardMapper.insertStudyBoard(board);
        log.info("result={}", result);

        if (result != null) {
            return board.getStudy_id();
        }
        return null;
    }

    @Override
    public BoardWriteDto findResultById(String study_id, BoardWriteDto boardWriteDto) {
        log.info("study_id={}", study_id);
        Optional<Board> findBoard = boardMapper.findById(study_id);
        if(findBoard.isPresent()) {
            Board board = findBoard.get();
            boardWriteDto.setBoard_id(board.getStudy_id());
            boardWriteDto.setBoard_commentCount(board.getComment_count());
            boardWriteDto.setBoard_viewCount(board.getView_count());
            boardWriteDto.setBoard_regDate(board.getReg_date());
            log.info("boardWriteDto={}", boardWriteDto);
            return boardWriteDto;
        }

        return null;
    }

    @Override
    public BoardWriteDto findStudyById(String study_id) {
        log.info("study_id={}", study_id);
        boardMapper.increaseViewCount(study_id);
        Optional<Board> findBoard = boardMapper.findById(study_id);
        if(findBoard.isPresent()) {
            Board board = findBoard.get();
            BoardWriteDto boardWriteDto = new BoardWriteDto().toBoardWriteDto(board);
            String userId = boardWriteDto.getBoard_writerId();
            String nickname = memberMapper.nickname(userId);
            boardWriteDto.setBoard_writerId(nickname);
            log.info("boardWriteDto={}", boardWriteDto);
            return boardWriteDto;
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