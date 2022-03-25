package com.doit.study.board.controller;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.domain.SearchCondition;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(SearchCondition sc,
                       Model m, HttpServletRequest request){
//        if(!loginCheck(request))
//            return "redirect:/login/login?toURL="+request.getRequestURL();

        try {
            int totalRecordCount = boardService.searchResultCount(sc);
            m.addAttribute("totalRecordCount", totalRecordCount);

            Pagination pagination = new Pagination(totalRecordCount, sc);

            List<BoardDto> list = boardService.searchSelectPage(sc);

            m.addAttribute("pagination", pagination);
            m.addAttribute("list", list);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/board/boardList2";
    }
        // list
//    @GetMapping("/list")
//    public String list(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
//                       @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
//                       Model m, HttpServletRequest request){
////        if(!loginCheck(request))
////            return "redirect:/login/login?toURL="+request.getRequestURL();
//
//        try {
//            int totalRecordCount = boardService.getCount();
//            Pagination pagination = new Pagination(currentPage, pageSize);
//            pagination.setTotalRecordCount(totalRecordCount);
//
//            m.addAttribute("pagination", pagination);
//            m.addAttribute("list", boardService.getPage(pagination));
//
//            return "/board/boardList";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "/";
//    }

//    @GetMapping("/searchList")
//    public String searchList(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
//                             @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
//                             Model m, HttpServletRequest request){
////        if(!loginCheck(request))
////            return "redirect:/login/login?toURL="+request.getRequestURL();
//
//        try {
////            int totalRecordCount = boardService.searchResultCount();
////            Pagination pagination = new Pagination(currentPage, pageSize);
////            pagination.setTotalRecordCount(totalRecordCount);
//            int totalRecordCount = boardService.getCount();
//            Pagination pagination = new Pagination(currentPage, pageSize);
//            pagination.setTotalRecordCount(totalRecordCount);
//
//
//            m.addAttribute("pagination", pagination);
//            m.addAttribute("list", boardService.searchSelectPage(pagination));
//
//            return "/board/searchBoardList";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "/board/searchBoardList";
//    }

    @GetMapping("/searchList")
    public String searchList(SearchCondition sc, Model m, HttpServletRequest request){
//        if(!loginCheck(request))
//            return "redirect:/login/login?toURL="+request.getRequestURL();

        try {
//            int totalRecordCount = boardService.searchResultCount();
//            Pagination pagination = new Pagination(currentPage, pageSize);
//            pagination.setTotalRecordCount(totalRecordCount);
            int totalRecordCount = boardService.searchResultCount(sc);
            Pagination pagination = new Pagination(totalRecordCount, sc);
            pagination.setTotalRecordCount(totalRecordCount);

            List<BoardDto> list = boardService.searchSelectPage(sc);

            m.addAttribute("pagination", pagination);
            m.addAttribute("list", boardService.searchSelectPage(sc));

            return "/board/searchBoardList";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/board/searchBoardList";
    }

    @GetMapping("/read")
    public String read(Integer board_Id, Model m){
        try {
            BoardDto boardDto = boardService.read(board_Id);
            m.addAttribute("board", boardDto);
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
    public String write(BoardDto boardDto, HttpSession session){
//        String writer = session.getAttribute("id");
        try {
            int result = boardService.write(boardDto);
            if(result!=1)
                throw new Exception("Write failed.");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "board/board";
        }
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