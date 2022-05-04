package com.doit.study;


import com.doit.study.board.dto.BoardDto;
import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.SocialDto;
import com.doit.study.member.service.MemberService;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.service.BoardService;

import com.doit.study.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final WishListService wishListService;

    @GetMapping
    public String home(
            HttpSession session,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
            Model model) throws Exception {

        MemberDto naverDto = (MemberDto) session.getAttribute(SessionConst.NAVER_MEMBER);
        MemberDto kakaoDto = (MemberDto) session.getAttribute(SessionConst.KAKAO_MEMBER);
        MemberDto memberDto = (MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        log.info("naverDto = " + naverDto);
        log.info("kakaoDto = " + kakaoDto);
        log.info("memberDto = " + memberDto);

        int id;
        String nickName;

        if(session!=null) {
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

        BoardDto boardWriteDto = new BoardDto();
        Integer totalRecordCount = boardService.getCount();
        if(totalRecordCount != null) {
            Pagination pagination = new Pagination(currentPage, pageSize);

            pagination.setTotalRecordCount(totalRecordCount);
            log.info("totalRecordCount = " + totalRecordCount);

            model.addAttribute("pagination", pagination);
            log.info("pagination = " + pagination);

            model.addAttribute("list", boardService.getStudyBoardList(pagination));
            log.info("list = " + boardService.getStudyBoardList(pagination));

            return "/index";
        }

        return null;
    }
}
