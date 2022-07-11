package com.doit.study.board.service;

import com.doit.study.board.domain.Board;
import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.SearchDto;
import com.doit.study.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SearchBoardServiceImpl implements SearchBoardService {

    private final BoardMapper boardMapper;
    private final MemberMapper memberMapper;
    private final ProfileMapper profileMapper;
    private final CommentMapper commentMapper;
    private final WishListMapper wishListMapper;

    /***
     * 검색 내용에 따른 게시글 개수
     * @param searchDto
     * @return Integer
     */
    @Override
    public Integer getCountBySearching(SearchDto searchDto) {

        Integer count;

        //온라인 오프라인 구분하기
        if(searchDto.getOn_off().equals(1)) {
            count = boardMapper.getOnlineBoardCountByKeyword(searchDto);
        } else {
            count = boardMapper.getOfflineBoardCountByKeyword(searchDto);
        }
        return count;
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
        List<Board> boardList;

        if(searchDto.getOn_off().equals(1)) {
            boardList = boardMapper.selectOnlineSearchPage(searchDto, pagination);
        } else {
            boardList = boardMapper.selectOfflineSearchPage(searchDto, pagination);
        }

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
