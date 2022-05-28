package com.doit.study.board.controller;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.*;
import com.doit.study.board.service.BoardService;
import com.doit.study.comment.dto.CommentDto;
import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.option.category.Interest;
import com.doit.study.option.location.Address;
import com.doit.study.comment.service.CommentService;
import com.doit.study.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final ProfileService profileService;

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


    @RequestMapping(value = {"/write", "/write/{board_id}"}, method = RequestMethod.GET)
    public String write(HttpServletRequest request,
                        @ModelAttribute("boardDto") BoardDto boardDto,
                        Model model,
                        @PathVariable Optional<Integer> board_id,
                        RedirectAttributes redirect) {
        if(!board_id.isPresent()) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("id") != null) {
                int id = (int) session.getAttribute("id");
                String nickName = (String) session.getAttribute("nickName");
                boardDto.setBoard_writerId(id);
                boardDto.setWriter_nickName(nickName);

                log.info("boardDto = " + boardDto);

                return "/board/boardWriteForm";
            } else {
                redirect.addAttribute("redirectURL", "/board/write");
                return "redirect:/login";
            }
        } else {
            //스터디 아이디로 게시글 정보 가져오기
            int study_id = board_id.get();

            boardDto = boardService.findStudyById(study_id);
            log.info("boardDto = "+boardDto);

            if(boardDto.getBoard_on_off() == 1) {
                boardDto.setBoard_onOffLine(true);
            }

            log.info("boardDto 정보 가져오기 " + boardDto);
            model.addAttribute("boardDto", boardDto);

            return "/board/boardWriteForm";
        }
    }

    @RequestMapping(value = {"/write", "/write/{board_id}"}, method = RequestMethod.POST)
    public String write(
                        @Valid @ModelAttribute("boardDto") BoardDto boardDto,
                        BindingResult bindingResult,
                        @PathVariable Optional<Integer> board_id,
                        RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "board/boardWriteForm";
        }

        //관심 정보 얻기
        Interest interest = new Interest();
        Map<String, String> interestMap = interest.getInterestMap();
        String interest1 = interestMap.get(boardDto.getBoard_interest1());
        String interest2 = interestMap.get(boardDto.getBoard_interest2());

        //관심 설정(첫 번째랑 두 번째 카테고리만 매핑되어 있음)
        boardDto.setBoard_interest1(interest1);
        boardDto.setBoard_interest2(interest2);

        //온라인 오프라인 설정
        if(boardDto.isBoard_onOffLine()) {
            boardDto.setBoard_location(null);
            boardDto.setBoard_on_off(1);
        } else {
            //주소 정보 얻기
            Address address = new Address();
            Map<String, String> addressMap = address.getAddressMap();
            String location1 = addressMap.get(boardDto.getBoard_location1());

            //주소 설정
            String location2 = boardDto.getBoard_location2();
            boardDto.setBoard_location(location1+" "+location2);
            boardDto.setBoard_on_off(0);
        }

        log.info("boardDto = " + boardDto);

        if(!board_id.isPresent()) {
            //사용자 아이디 가져오기
            int board_writerId = boardDto.getBoard_writerId();

            int study_id = boardService.insertStudyBoard(boardDto);
            log.info("study_id={}", study_id);

            redirectAttributes.addFlashAttribute("boardDto", boardDto);

            return "redirect:/board/result/" + study_id;
        } else {
            int study_id = board_id.get();
            log.info("study_id "+study_id);
            boardDto.setBoard_id(study_id);
            boardDto = boardService.updateBoard(boardDto);

            redirectAttributes.addFlashAttribute("boardDto", boardDto);

            return "redirect:/board/result/" + study_id;
        }



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

            //프로필 사진 가져오기
            String path = profileService.findImage(boardDto.getBoard_writerId());
            log.info(path);
            boardDto.setPath(path);

            log.info("boardDto 갱신={}", boardDto);
            model.addAttribute("boardDto", boardDto);
            //댓글 정보 가져오기
            commentCheck(model, id);
            //댓글 개수
            model.addAttribute("totalComment", commentService.getCount(id));
            return "/board/boardDetail";
        } else {
            BoardDto boardDto = boardService.findStudyById(id);

            //프로필 사진 가져오기
            String path = profileService.findImage(boardDto.getBoard_writerId());
            log.info(path);
            boardDto.setPath(path);

            log.info("boardDto = " + boardDto);
            model.addAttribute("boardDto", boardDto);
            //댓글 정보 가져오기
            commentCheck(model, id);
            //댓글 개수
            model.addAttribute("totalComment", commentService.getCount(id));
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

    //스터디 검색 기능
    @PostMapping("/search")
    public String searchStudy(@ModelAttribute SearchDto searchDto,
                              HttpServletRequest request,
                              @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                              Model model) throws Exception{


        HttpSession session = request.getSession(false);

        Integer id;
        String nickName;
        String path;

        if(session!=null) {

            MemberDto naverDto = (MemberDto) session.getAttribute(SessionConst.NAVER_MEMBER);
            MemberDto kakaoDto = (MemberDto) session.getAttribute(SessionConst.KAKAO_MEMBER);
            MemberDto memberDto = (MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

            log.info("naverDto = " + naverDto);
            log.info("kakaoDto = " + kakaoDto);
            log.info("memberDto = " + memberDto);

            if (naverDto != null) {
                id = naverDto.getId();
                nickName = naverDto.getNickname();
                session.setAttribute("id", id);
                session.setAttribute("nickName", nickName);
            } else if (kakaoDto != null) {
                id = kakaoDto.getId();
                nickName = kakaoDto.getNickname();
                session.setAttribute("id", id);
                session.setAttribute("nickName", nickName);
            } else if (memberDto != null) {
                id = memberDto.getId();
                nickName = memberDto.getNickname();
                session.setAttribute("id", id);
                session.setAttribute("nickName", nickName);
            }
        }

        //주소 정보 얻기
        Address address = new Address();
        Map<String, String> addressMap = address.getAddressMap();
        String location1;
        String location2;

        //주소 설정
        if(searchDto.getLocation1() != null) {
            location1 = addressMap.get(searchDto.getLocation1());
            if(searchDto.getLocation2() != null) {
                location2 = searchDto.getLocation2();
                searchDto.setLocation(location1+" "+location2);
            } else {
                searchDto.setLocation(location1);
            }
        }


        //on_off 설정
        if(searchDto.isOnline()) {
            searchDto.setOn_off("1");
        } else {
            searchDto.setOn_off("0");
        }

        log.info("searchDto = {}", searchDto);


        Integer totalRecordCount = boardService.getCountBySearching(searchDto);
        log.info("totalRecord 수는? " + totalRecordCount);
        if(totalRecordCount != 0) {
            Pagination pagination = new Pagination(currentPage, pageSize);

            pagination.setTotalRecordCount(totalRecordCount);
            log.info("totalRecordCount = " + totalRecordCount);

            model.addAttribute("pagination", pagination);
            log.info("pagination = " + pagination);

            model.addAttribute("list", boardService.getStudyBoardList(pagination));
            log.info("list = " + boardService.getStudyBoardList(pagination));

            return "/index";
        } else {
            Pagination pagination = new Pagination(currentPage, pageSize);

            pagination.setTotalRecordCount(totalRecordCount);
            log.info("totalRecordCount = " + totalRecordCount);

            model.addAttribute("pagination", pagination);
            log.info("pagination = " + pagination);

            model.addAttribute("list", null);


            return "/index";
        }

    }

//    @GetMapping("/studyList/{id}")
//    public String get(Model model,
//                      @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
//                      @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
//                      @PathVariable String id) {
//
//        BoardDto boardWriteDto = new BoardDto();
//        Integer totalRecordCount = boardService.getCountMyStudy(id);
//        if (totalRecordCount != null) {
//            Pagination pagination = new Pagination(currentPage, pageSize);
//
//            pagination.setTotalRecordCount(totalRecordCount);
//            log.info("totalRecordCount = " + totalRecordCount);
//
//            model.addAttribute("pagination", pagination);
//            log.info("pagination = " + pagination);
//
//            model.addAttribute("list", boardService.getStudyBoardList(pagination));
//            log.info("list = " + boardService.getStudyBoardList(pagination));
//
//            model.addAttribute("id", id);
//            model.addAttribute("boardWriteDto", boardWriteDto);
//
//            return "/board/myStudyList";
//
//        }
//
//        return null;
//    }
//
//    @GetMapping("/indexStudyList")
//    public String newMyStudylist(
//                        @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
//                        @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
//                        @RequestParam String id,
//                        Model model
//    ){
//
//        log.info("id={}", id);
//        try {
//            Integer totalRecordCount = boardService.getCountMyStudy(id);
//            Pagination pagination = new Pagination(currentPage, pageSize);
//            pagination.setTotalRecordCount(totalRecordCount);
//
//            model.addAttribute("pagination", pagination);
//            model.addAttribute("list", boardService.getStudyBoardList(pagination));
//            model.addAttribute("id", id);
//
//            return "/board/myStudyList";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "/board/myStudyList";
//    }

}