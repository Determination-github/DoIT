package com.doit.study;


import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.KakaoDto;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.NaverDto;
import com.doit.study.member.service.MemberService;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.service.BoardService;

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

    @GetMapping
    public String home(
            HttpSession session,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
            Model model) throws Exception {

        NaverDto naverDto = (NaverDto) session.getAttribute(SessionConst.NAVER_MEMBER);
        KakaoDto kakaoDto = (KakaoDto) session.getAttribute(SessionConst.KAKAO_MEMBER);
        MemberDto memberDto = (MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        log.info("naverDto = " + naverDto);
        log.info("kakaoDto = " + kakaoDto);
        log.info("memberDto = " + memberDto);

        if(naverDto != null) {
            session.setAttribute("naverDto", naverDto);
        } else if(kakaoDto != null) {
            session.setAttribute("kakaoDto", kakaoDto);
        } else if(memberDto != null) {
            session.setAttribute("memberDto", memberDto);
        }

        BoardDto boardDto = new BoardDto();
        int totalRecordCount = boardService.getCount();
        Pagination pagination = new Pagination(currentPage, pageSize);
        pagination.setTotalRecordCount(totalRecordCount);

        log.info("totalRecordCount = " + totalRecordCount);
        model.addAttribute("pagination", pagination);
        log.info("pagination = " + pagination);
        model.addAttribute("lists", boardService.getPage(pagination));
        log.info("lists = " + boardService.getPage(pagination));
        model.addAttribute("board", boardDto);
        return "/index";
    }
}
