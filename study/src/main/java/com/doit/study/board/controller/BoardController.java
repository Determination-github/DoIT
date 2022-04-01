package com.doit.study.board.controller;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.domain.SearchCondition;
import com.doit.study.board.dto.BoardDto;
//import com.doit.study.board.dto.SearchBoardDto;
import com.doit.study.board.service.BoardService;
import com.doit.study.member.domain.Member;
import com.doit.study.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                       Model m, HttpServletRequest request){
        try {
            int totalRecordCount = boardService.getCount();
            Pagination pagination = new Pagination(currentPage, pageSize);
            pagination.setTotalRecordCount(totalRecordCount);

            m.addAttribute("pagination", pagination);
            m.addAttribute("list", boardService.getPage(pagination));

            return "/board/boardList";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/";
    }

    @GetMapping("/searchList")
    public String searchList(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                             BoardDto boardDto, ServletRequest request, Model m, SearchCondition sc)
            throws Exception {
        int totalRecordCount = boardService.searchResultCount(boardDto);
        boardDto.doPaging(totalRecordCount, sc);
        log.info("totalRecordCount =" + totalRecordCount );
        log.info("searchBoardDto ="+ boardDto);
        m.addAttribute("searchList", boardService.searchSelectPage(boardDto));
        return "/board/searchBoardList";
    }

    @GetMapping("/read")
    public String read(Integer board_Id, Model m, HttpSession session, BoardDto boardDto){
        String board_Writer = (String)session.getAttribute("memberDto");
        boardDto.setBoard_Writer(board_Writer);
        log.info("board_Writer = "+board_Writer);
        try {
            boardDto = boardService.read(board_Id);
            m.addAttribute("board", boardDto);
            m.addAttribute("board_Writer", board_Writer);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/board/list";
        }
        return "/board/board";
    }

    @GetMapping("/write")
    public String write(Model m) {
        return "/board/insert";
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, MemberDto memberDto, HttpSession session, HttpServletRequest request, Model m) throws Exception {
        String board_Writer = (String)session.getAttribute("memberDto");
        boardDto.setBoard_Writer(board_Writer);
        log.info("board_Writer = "+board_Writer);
        boardService.write(boardDto);
        m.addAttribute("board_Writer", board_Writer);
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDto boardDto){
//        String writer = session.getAttribute("id");
        try {
            int result = boardService.modify(boardDto);
            if(result!=1);
            throw new Exception("Modify failed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/board/list";
    }
    @PostMapping("/remove")
    public String remove(Integer board_Id, String board_Writer,BoardDto boardDto){
//        String writer = session.getAttribute("id");
        try {
            int result = boardService.remove(boardDto);
            if(result!=1);
            throw new Exception("Delete failed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/board/list";
    }

//    @PostMapping("/remove")
//    public String remove(@RequestParam("board_Writer")String board_Writer) throws Exception {
////        String board_writer = session.getAttribute("id");
//
//        boardService.remove(board_Writer);
//
//        return "redirect:/board/list";
//    }

    private boolean loginCheck(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session!=null && session.getAttribute("id")!=null;
    }
}