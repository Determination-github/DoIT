package com.doit.study.board.service;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;

    @Override
    public List<BoardDto> getList() throws Exception {

        List<BoardDto> boardList = boardMapper.selectAll();
        return boardList;
    }

    @Override
    public int getCount() throws Exception {
        return boardMapper.count();
    }

    @Override
    public List<BoardDto> getPage(Pagination pagination) throws Exception {
        log.info("BoardDto = " + boardMapper.selectPage(pagination));
        return boardMapper.selectPage(pagination);
    }

    @Override
    public BoardDto read(Integer board_Id) throws Exception {
        BoardDto boardDto = boardMapper.selectOne(board_Id);
        boardMapper.increaseViewCount(board_Id);

        return boardDto;
    }

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
    public int searchResultCount(BoardDto boardDto) throws Exception {
        return boardMapper.searchResultCount(boardDto);
    }

    @Override
    public List<BoardDto> searchSelectPage(BoardDto boardDto) throws Exception {
        return boardMapper.searchSelectPage(boardDto);
    }

    @Override
    public int updateCommentCount(Integer board_Id, int count) {
        return boardMapper.updateCommentCount(board_Id, count);
    }

}