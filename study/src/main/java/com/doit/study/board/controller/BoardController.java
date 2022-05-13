package com.doit.study.board.controller;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.*;
import com.doit.study.board.service.BoardService;
import com.doit.study.comment.dto.CommentDto;
import com.doit.study.option.category.Interest;
import com.doit.study.option.location.Address;
import com.doit.study.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                       Model model, HttpServletRequest request){
        try {
            int totalRecordCount = boardService.getCount();
            Pagination pagination = new Pagination(currentPage, pageSize);
            pagination.setTotalRecordCount(totalRecordCount);

            model.addAttribute("pagination", pagination);
            log.info("pagination = {}", pagination);
            model.addAttribute("list", boardService.getStudyBoardList(pagination));
            log.info("list = {}", boardService.getStudyBoardList(pagination));

            return "/index";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/index";
    }

//    @GetMapping("/searchList")
//    public String searchList(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
//                             @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
//                             @ModelAttribute("searchBoardDto") SearchBoardDto searchBoardDto, BoardDto boardDto,
//                             ServletRequest request, Model model)
//            throws Exception {
//        int totalRecordCount = boardService.searchResultCount(searchBoardDto);
//        searchBoardDto.doPaging(totalRecordCount);
//        log.info("totalRecordCount =" + totalRecordCount );
//
//        List<SearchBoardDto> searchList = boardService.searchSelectPage(searchBoardDto);
//        List<BoardDto> searchList2 = boardService.getList();
//        log.info("searchBoardDto ="+ searchBoardDto);
//        model.addAttribute("searchList", searchList);
//        return "/board/searchBoardList";
//    }

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
                        @ModelAttribute("firstStudyDto") FirstStudyDto firstStudyDto,
                        RedirectAttributes redirect) {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("id")!=null) {
            int id = (int) session.getAttribute("id");
            String nickName = (String) session.getAttribute("nickName");
            firstStudyDto.setId(id);
            firstStudyDto.setNickName(nickName);
            return "/board/firstWriteBoardForm";
        } else {
            redirect.addAttribute("redirectURL", "/board/write");
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
    public String secondWrite(@ModelAttribute("boardDto") BoardDto boardDto,
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

            //주소 설정
            String location2 = firstStudyDto.getLocation2();
            firstStudyDto.setTotalLocation(location1+" "+location2);

            //관심 정보 얻기
            Interest interest = new Interest();
            Map<String, String> interestMap = interest.getInterestMap();
            String interest1 = interestMap.get(firstStudyDto.getInterest1());
            String interest2 = interestMap.get(firstStudyDto.getInterest2());

            //관심 설정(첫 번째랑 두 번째 카테고리만 매핑되어 있음)
            firstStudyDto.setInterest1(interest1);
            firstStudyDto.setInterest2(interest2);

            //온라인 오프라인 설정
            if(firstStudyDto.isOnOffLine()) {
                firstStudyDto.setFlag(1);
            } else {
                firstStudyDto.setFlag(0);
            }

            boardDto.toBoardDto(firstStudyDto, boardDto);

            log.info("secondWrite 화면으로..");
            log.info("boardDto = " + boardDto);
            return "/board/secondWriteBoardForm";
        } else {
            return "redirect:/board/write";
        }
    }

    @PostMapping("/secondWrite")
    public String secondWrite(@ModelAttribute("boardDto") BoardDto boardDto,
                              RedirectAttributes redirectAttributes) {
        log.info("두 번째 writeForm");
        log.info("boardDto = " + boardDto);

        //사용자 아이디 가져오기
        int board_writerId = boardDto.getBoard_writerId();

        int study_id = boardService.insertStudyBoard(boardDto);
        log.info("study_id={}", study_id);

        redirectAttributes.addFlashAttribute("boardDto", boardDto);

        return "redirect:/board/result/" + study_id;
    }

    @GetMapping("/result/{id}")
    public String getStudyBoard(Model model,
                                HttpServletRequest request,
                                @PathVariable int id) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        log.info("inputFlashMap={}",inputFlashMap);
        if(inputFlashMap!=null) {
            BoardDto boardDto = (BoardDto) inputFlashMap.get("boardDto");
            log.info("boardDto = " + boardDto);
            //게시글 정보 가져오기
            boardDto = boardService.findResultById(id, boardDto);
            log.info("boardDto 갱신={}", boardDto);
            model.addAttribute("boardDto", boardDto);
            //댓글 정보 가져오기
            commentCheck(model, id);
            return "/board/boardDetail";
        } else {
            BoardDto boardDto = boardService.findStudyById(id);
            log.info("boardDto = " + boardDto);
            model.addAttribute("boardDto", boardDto);
            //댓글 정보 가져오기
            commentCheck(model, id);
            return "/board/boardDetail";
        }
    }

    //댓글 여부 확인 메서드
    private void commentCheck(Model model, int id) {
        List<CommentDto> comments = commentService.getComment(id);
        if (comments.isEmpty()) {
            model.addAttribute("comments", null);
        } else {
            model.addAttribute("comments", comments);
        }
    }

//    @PostMapping("/modify")
//    public String modify(BoardDto boardWriteDto){
////        String writer = session.getAttribute("id");
//        try {
//            int result = boardService.modify(boardWriteDto);
//            if(result!=1);
//            throw new Exception("Modify failed.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "redirect:/board/list";
//    }

    @GetMapping("/delete")
    public String delete(@RequestParam String id) {
        log.info("id={}", id);
        return "board/boardDetail";
    }

    @PostMapping("/remove")
    public String remove(String board_Id, BoardDto boardDto) throws Exception {
        commentService.removeAll(board_Id);
//        boardService.remove(boardDto);
        return "redirect:/board/list";
    }

    @GetMapping("/studyList/{id}")
    public String get(Model model,
                      @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                      @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                      @PathVariable String id) {

        BoardDto boardWriteDto = new BoardDto();
        Integer totalRecordCount = boardService.getCountMyStudy(id);
        if (totalRecordCount != null) {
            Pagination pagination = new Pagination(currentPage, pageSize);

            pagination.setTotalRecordCount(totalRecordCount);
            log.info("totalRecordCount = " + totalRecordCount);

            model.addAttribute("pagination", pagination);
            log.info("pagination = " + pagination);

            model.addAttribute("list", boardService.getStudyBoardList(pagination));
            log.info("list = " + boardService.getStudyBoardList(pagination));

            model.addAttribute("id", id);
            model.addAttribute("boardWriteDto", boardWriteDto);

            return "/board/myStudyList";

        }

        return null;
    }

    @GetMapping("/indexStudyList")
    public String newMyStudylist(
                        @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                        @RequestParam String id,
                        Model model
    ){

        log.info("id={}", id);
        try {
            Integer totalRecordCount = boardService.getCountMyStudy(id);
            Pagination pagination = new Pagination(currentPage, pageSize);
            pagination.setTotalRecordCount(totalRecordCount);

            model.addAttribute("pagination", pagination);
            model.addAttribute("list", boardService.getStudyBoardList(pagination));
            model.addAttribute("id", id);

            return "/board/myStudyList";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/board/myStudyList";
    }

    @PostMapping("/hashtag")
    @ResponseBody
    public String hashtag(@RequestBody List hashtag) {
        log.info("hashtag={}", hashtag);
        return "ok";
    }

}