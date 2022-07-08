package com.doit.study.board.controller;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.SearchDto;
import com.doit.study.board.service.BoardService;
import com.doit.study.comment.dto.CommentDto;
import com.doit.study.file.dto.FileDto;
import com.doit.study.file.service.FileService;
import com.doit.study.file.service.S3Uploader;
import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.option.category.Interest;
import com.doit.study.option.location.Address;
import com.doit.study.comment.service.CommentService;
import com.doit.study.profile.service.ProfileService;
import com.doit.study.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final ProfileService profileService;
    private final WishListService wishListService;
    private final FileService fileService;
    private final S3Uploader s3Uploader;

    /**
     * 스터디 페이지 페이징 처리용 컨트롤러
     * @param currentPage
     * @param pageSize
     * @param model
     * @param request
     * @return String
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                       Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        //총 글 개수
        int totalRecordCount = boardService.getCount();

        //페이징
        Pagination pagination = paging(currentPage, pageSize, totalRecordCount, model);

        //글 좋아요 여부 확인을 위한 id가져오기
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("id") != null) {
            Integer id = (int) session.getAttribute("id");
            model.addAttribute("list", boardService.getStudyBoardList(id, pagination));
        } else {
            model.addAttribute("list", boardService.getStudyBoardList(null, pagination));
        }

        return "index";
    }

    /**
     * 게시글 작성
     * @param request
     * @param boardDto
     * @param model
     * @param board_id
     * @param redirect
     * @return String
     */
    @RequestMapping(value = {"/write", "/write/{board_id}"}, method = RequestMethod.GET)
    public String write(HttpServletRequest request,
                        @ModelAttribute("boardDto") BoardDto boardDto,
                        Model model,
                        @PathVariable Optional<Integer> board_id,
                        RedirectAttributes redirect) {

        if(!board_id.isPresent()) { //게시글 작성
            //세션에서 아이디 가져오기
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("id") != null) {
                int id = (int) session.getAttribute("id");
                String nickName = (String) session.getAttribute("nickName");
                //아이디와 닉네임 설정
                boardDto.setBoard_writerId(id);
                boardDto.setWriter_nickName(nickName);
            }

            return "board/boardWriteForm";

        } else { //게시글 수정
            //스터디 아이디로 게시글 정보 가져오기
            int study_id = board_id.get();

            //스터디 게시글 정보 가져오기
            boardDto = boardService.findStudyById(study_id);

            //찾는 스터디 게시글 정보가 없을 경우 Exception 발생
            if(boardDto == null) {
                throw new NullPointerException();
            }

            //온라인, 오프라인 설정
            if (boardDto.getBoard_on_off() == 1) {
                boardDto.setBoard_onOffLine(true);
            }

            //boardDto 객체 반환
            model.addAttribute("boardDto", boardDto);

            return "board/boardWriteForm";
        }
    }

    /**
     * 게시글 작성하기
     * @param boardDto
     * @param bindingResult
     * @param board_id
     * @param redirectAttributes
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = {"/write", "/write/{board_id}"}, method = RequestMethod.POST)
    public String write(
                        @Valid @ModelAttribute("boardDto") BoardDto boardDto,
                        BindingResult bindingResult,
                        @PathVariable Optional<Integer> board_id,
                        RedirectAttributes redirectAttributes) throws Exception {

        //유효성 검사
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

        if(!board_id.isPresent()) { //게시글 새로 작성
            //사용자 아이디 가져오기
            int board_writerId = boardDto.getBoard_writerId();

            //스터디 아이디 가져오기
            int study_id = boardService.insertStudyBoard(boardDto);
            log.info("study_id={}", study_id);

            redirectAttributes.addFlashAttribute("boardDto", boardDto);

            return "redirect:result/" + study_id;
        } else { //게시글 수정
            int study_id = board_id.get();

            //찾는 스터디 게시글 정보가 없을 경우 Exception 발생
            if(boardDto == null) {
                throw new NullPointerException();
            }

            boardDto.setBoard_id(study_id);
            boardDto = boardService.updateBoard(boardDto);

            redirectAttributes.addFlashAttribute("boardDto", boardDto);

            return "redirect:result/" + study_id;
        }
    }

    /**
     * 작성한 게시글 세부화면
     * @param model
     * @param request
     * @param id
     * @return String
     */
    @GetMapping("/result/{id}")
    public String getStudyBoard(Model model,
                                HttpServletRequest request,
                                @PathVariable int id) throws IOException {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        log.info("inputFlashMap={}",inputFlashMap); //redirect로 넘겨받은 정보

        //세션에 스터디 아이디 저장
        HttpSession session = request.getSession();
        //기존 세션 삭제
        session.removeAttribute("study_id");
        //세션 업데이트
        session.setAttribute("study_id", id);

        if(inputFlashMap != null) { //게시글 작성 후 게시글 세부화면으로 이어지는 경우
            BoardDto boardDto = (BoardDto) inputFlashMap.get("boardDto");

            //서버에러 던지기
            if(boardDto == null) {
                throw new IOException();
            }

            //게시글 정보 가져오기
            boardDto = boardService.findResultById(id, boardDto);

            //게시글 정보 세팅
            getStudyInfo(model, id, session, boardDto);
            model.addAttribute("boardDto", boardDto);

            return "board/boardDetail";

        } else { //게시글 목록에서 게시글 세부화면으로 이동하는 경우
            BoardDto boardDto = boardService.findStudyById(id);

            if(boardDto == null) {
                throw new NullPointerException();
            }

            //게시글 정보 세팅
            getStudyInfo(model, id, session, boardDto);
            model.addAttribute("boardDto", boardDto);

            return "board/boardDetail";
        }
    }

    /**
     * 스터디 검색 기능
     * @param searchDto
     * @param request
     * @param currentPage
     * @param pageSize
     * @param model
     * @return String
     * @throws Exception
     */
    @GetMapping("/search")
    public String searchStudy(@ModelAttribute SearchDto searchDto,
                              HttpServletRequest request,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "online", required = false) Boolean online,
                              @RequestParam(value = "location", required = false) String location,
                              @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                              Model model) throws Exception{

        HttpSession session = request.getSession(false);

        Integer id = null;
        String nickName;
        String path;

        if(session!=null) {

            //회원이 어떤 경로로 로그인 했는지 체크
            MemberDto naverDto = (MemberDto) session.getAttribute(SessionConst.NAVER_MEMBER);
            MemberDto kakaoDto = (MemberDto) session.getAttribute(SessionConst.KAKAO_MEMBER);
            MemberDto memberDto = (MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

            //회원 정보에 따른 세션값 담기
            if (naverDto != null) {
                id = naverDto.getId();
                nickName = naverDto.getNickname();
                setSessionInfo(session, id, nickName);
            } else if (kakaoDto != null) {
                id = kakaoDto.getId();
                nickName = kakaoDto.getNickname();
                setSessionInfo(session, id, nickName);
            } else if (memberDto != null) {
                id = memberDto.getId();
                nickName = memberDto.getNickname();
                setSessionInfo(session, id, nickName);
            }
        }

        //주소 정보 얻기
        Address address = new Address();
        Map<String, String> addressMap = address.getAddressMap();
        String location1;
        String location2;

        //주소 설정
        if(!Objects.equals(searchDto.getLocation1(), "")) {
            location1 = addressMap.get(searchDto.getLocation1());
            if(searchDto.getLocation2() != null) {
                location2 = searchDto.getLocation2();
                searchDto.setLocation(location1+" "+location2);
            } else {
                searchDto.setLocation(location1);
            }
        } else {
            searchDto.setLocation(null);
        }

        //on_off 설정
        if(searchDto.isOnline()) {
            searchDto.setOn_off(1);
        } else {
            searchDto.setOn_off(0);
        }

        //검색 게시글 개수
        Integer totalRecordCount = boardService.getCountBySearching(searchDto);
        if(totalRecordCount != 0) {
            //페이징
            Pagination pagination = paging(currentPage, pageSize, totalRecordCount, model);

            //게시글 목록 정보 담기
            model.addAttribute("list", boardService.getSearchStudyBoardList(id, searchDto, pagination));
            model.addAttribute("searchDto", searchDto);

            return "board/boardSearching";
        } else {
            //게시글 검색 결과가 없는 경우
            model.addAttribute("list", null);

            return "board/boardSearching";
        }
    }

    /**
     * 게시글 삭제하기
     * @param study_id
     * @return ResponseEntity
     * @throws Exception
     */
    @DeleteMapping("/delete/{study_id}")
    public ResponseEntity deleteStudy(@PathVariable("study_id") Integer study_id) throws Exception {
        //삭제해야 할 파일이 있는지 확인
        List<FileDto> list = fileService.findFileByStudyId(study_id);

        if(list != null) { //S3업로드 파일 삭제
            for (FileDto fileDto : list) {
                String file_id = fileDto.getFile_id();
                String file_origin_name = fileDto.getFile_origin_name();
                String S3FileName = file_id+file_origin_name;
                String dirName = "/studyImageUpload";
                log.info("S3FileName = " +S3FileName);

                s3Uploader.deleteFile(S3FileName, dirName);
            }
        }

        Integer result = boardService.deleteBoard(study_id);

        //삭제가 성공했을 경우
        if(result != null) {
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            throw new IOException();
        }
    }

    /**
     * 회원이 작성한 게시글 목록 보기
     * @param model
     * @param currentPage
     * @param pageSize
     * @param id
     * @return String
     */
    @GetMapping("/studyList/{id}")
    public String studyList(Model model,
                      @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                      @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                      @PathVariable Integer id) {

        return getUserStudyList(model, currentPage, pageSize, id);
    }

    /**
     * 작성한 게시글 목록 페이징
     * @param currentPage
     * @param pageSize
     * @param id
     * @param model
     * @return String
     */
    @GetMapping("/studyList")
    public String studyListIndex(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                                 @RequestParam Integer id,
                                 Model model) {

        return getUserStudyList(model, currentPage, pageSize, id);
    }


    //--------------------------------extracted method-----------------------------------------

    /**
     * 게시글 정보가져오기
     * @param model
     * @param id
     * @param session
     * @param boardDto
     */
    private void getStudyInfo(Model model, int id, HttpSession session, BoardDto boardDto) {
        //프로필 사진 가져오기
        String path = profileService.findImage(boardDto.getBoard_writerId());
        boardDto.setPath(path);

        //댓글 정보 가져오기
        commentCheck(model, id);

        //댓글 개수
        model.addAttribute("totalComment", commentService.getCount(id));

        //좋아요 정보 가져오기
        if (session.getAttribute("id") != null) {
            //유저 아이디 가져오기
            Integer user_id = (int) session.getAttribute("id");
            Integer result = wishListService.getCountByIdAndStudyId(user_id, id);
            //좋아요 처리하기
            if (result != 0) {
                boardDto.setBoard_like(true);
            }
        }
    }

    /**
     * 세션에 아이디와 닉네임 담기
     * @param session
     * @param id
     * @param nickName
     */
    private void setSessionInfo(HttpSession session, Integer id, String nickName) {
        session.setAttribute("id", id);
        session.setAttribute("nickName", nickName);
    }

    /**
     * 페이징
     * @param currentPage
     * @param pageSize
     * @param totalRecordCount
     * @param model
     * @return Pagination
     */
    private Pagination paging(int currentPage, int pageSize, Integer totalRecordCount, Model model) {
        //페이징 처리 객체 생성
        Pagination pagination = new Pagination(currentPage, pageSize);

        //페이징 정보 담기
        pagination.setTotalRecordCount(totalRecordCount);
        model.addAttribute("pagination", pagination);

        return pagination;
    }

    /**
     * 댓글 여부 확인 메서드
     * @param model
     * @param id
     */
    private void commentCheck(Model model, int id) {
        List<CommentDto> comments = commentService.getComment(id);
        if (comments.isEmpty()) {
            model.addAttribute("comments", null);
        } else {
            model.addAttribute("comments", comments);
        }
    }


    /**
     * 회원 게시글 가져오기
     * @param model
     * @param currentPage
     * @param pageSize
     * @param id
     * @return String
     */
    private String getUserStudyList(Model model, int currentPage, int pageSize, Integer id) {
        Integer totalRecordCount = boardService.getCountById(id);
        if (totalRecordCount != 0) {
            //페이징
            Pagination pagination = paging(currentPage, pageSize, totalRecordCount, model);

            //게시글 개수 세팅
            pagination.setTotalRecordCount(totalRecordCount);

            //페이징
            model.addAttribute("pagination", pagination);

            //회원의 작성글 가져오기
            model.addAttribute("list", boardService.getMyStudyBoardList(id, pagination));

            //아이디값 담기
            model.addAttribute("id", id);

            return "board/myStudyList";
        } else { //게시글 작성 목록이 없는 경우
            model.addAttribute("list", null);
            return "board/myStudyList";
        }
    }

}