package com.doit.study.board.controller;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.*;
import com.doit.study.board.service.BoardService;
import com.doit.study.option.category.Interest;
import com.doit.study.option.location.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


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
            m.addAttribute("list", boardService.getStudyBoardList(pagination));

            return "/index";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/index";
    }

    @GetMapping("/searchList")
    public String searchList(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                             @ModelAttribute("searchBoardDto") SearchBoardDto searchBoardDto, BoardDto boardDto,
                             ServletRequest request, Model m)
            throws Exception {
        int totalRecordCount = boardService.searchResultCount(searchBoardDto);
        searchBoardDto.doPaging(totalRecordCount);
        log.info("totalRecordCount =" + totalRecordCount );

        List<SearchBoardDto> searchList = boardService.searchSelectPage(searchBoardDto);
        log.info("searchBoardDto ="+ searchBoardDto);
        m.addAttribute("searchLists", searchList);
        return "/board/searchBoardList";
    }

//    @GetMapping("/read")
//    public String read(Integer board_id, Model m){
//        try {
//            BoardDto boardDto = boardService.read(board_id);
//            m.addAttribute("board", boardDto);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "redirect:/board/list";
//        }
//        return "/board/board";
//    }

    @GetMapping("/write")
    public String write(HttpServletRequest request,
                        @ModelAttribute("firstStudyDto") FirstStudyDto firstStudyDto) {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("id")!=null) {
            String id = (String) session.getAttribute("id");
            String nickName = (String) session.getAttribute("nickName");
            log.info("id={}", id);
            log.info("nickName={}", nickName);
            firstStudyDto.setWriterId(id);
            firstStudyDto.setNickName(nickName);
            return "/board/firstWriteBoardForm";
//            return "/board/firstInsert";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/write")
    public String write(
            @ModelAttribute("firstStudyDto") FirstStudyDto firstStudyDto,
            RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("firstStudyDto", firstStudyDto);
        log.info("firstStudyDto = " + firstStudyDto);

        return "redirect:/board/secondWrite";
    }

    @GetMapping("/secondWrite")
    public String secondWrite(@ModelAttribute("boardWriteDto") BoardWriteDto boardWriteDto,
                              HttpServletRequest request,
                              Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(inputFlashMap != null) {
            FirstStudyDto firstStudyDto = (FirstStudyDto) inputFlashMap.get("firstStudyDto");
            model.addAttribute("firstStudyDto", firstStudyDto);

            //주소 정보 얻기
            Address address = new Address();
            Map<String, String> addressMap = address.getAddressMap();
            String location1 = addressMap.get(firstStudyDto.getLocation1());

            //관심 정보 얻기
            Interest interest = new Interest();
            Map<String, String> interestMap = interest.getInterestMap();
            String interest1 = interestMap.get(firstStudyDto.getInterest1());
            String interest2 = interestMap.get(firstStudyDto.getInterest2());

            //주소 설정
            String location2 = firstStudyDto.getLocation2();
            firstStudyDto.setTotalLocation(location1+" "+location2);

            //관심 설정
            firstStudyDto.setInterest1(interest1);
            firstStudyDto.setInterest2(interest2);

            //온라인 오프라인 설정
            if(firstStudyDto.isOnOffLine()) {
                firstStudyDto.setFlag(1);
            } else {
                firstStudyDto.setFlag(0);
            }

            boardWriteDto.toBoardWriteDto(firstStudyDto, boardWriteDto);

            log.info("secondWrite 화면으로..");
            log.info("boardWriteDto = " + boardWriteDto);
            return "/board/secondWriteBoardForm";
        } else {
            return "redirect:/board/write";
        }
    }

    @PostMapping("/secondWrite")
    public String secondWrite(@ModelAttribute("boardWriteDto") BoardWriteDto boardWriteDto,
                              RedirectAttributes redirectAttributes) {
        log.info("두 번째 writeForm");
        log.info("boardWriteDto = " + boardWriteDto);

        String study_id = boardService.insertStudyBoard(boardWriteDto);
        log.info("study_id={}", study_id);

        redirectAttributes.addFlashAttribute("boardWriteDto", boardWriteDto);

        return "redirect:/board/result/" + study_id;
    }

    @GetMapping("/result/{id}")
    public String getStudyBoard(Model model,
                                HttpServletRequest request,
                                @PathVariable String id) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        log.info("inputFlashMap={}",inputFlashMap);
        if(inputFlashMap!=null) {
            BoardWriteDto boardWriteDto = (BoardWriteDto) inputFlashMap.get("boardWriteDto");
            log.info("boardWriteDto = " + boardWriteDto);
            boardWriteDto = boardService.findResultById(id, boardWriteDto);
            log.info("boardWriteDto 갱신={}", boardWriteDto);
            model.addAttribute("boardWriteDto", boardWriteDto);
            return "/board/boardDetail";
        } else {
            BoardWriteDto boardWriteDto = boardService.findStudyById(id);
            log.info("boardWriteDto = " + boardWriteDto);
            model.addAttribute("boardWriteDto", boardWriteDto);
            return "/board/boardDetail";
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