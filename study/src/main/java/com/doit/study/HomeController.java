package com.doit.study;


import com.doit.study.board.dto.BoardWriteDto;
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

        String id;
        String nickName;

        if(session!=null) {
            if (naverDto != null) {
                id = naverDto.getNaverId();
                nickName = naverDto.getNaverNickname();
                session.setAttribute("id", id);
                session.setAttribute("nickName", nickName);
            } else if (kakaoDto != null) {
                id = kakaoDto.getKakaoId();
                nickName = kakaoDto.getKakaoNickname();
                session.setAttribute("id", id);
                session.setAttribute("nickName", nickName);
            } else if (memberDto != null) {
                id = memberDto.getUser_id();
                nickName = memberDto.getNickname();
                session.setAttribute("id", id);
                session.setAttribute("nickName", nickName);
            }
        }

        BoardWriteDto boardWriteDto = new BoardWriteDto();
        int totalRecordCount = boardService.getCount();
        Pagination pagination = new Pagination(currentPage, pageSize);

        pagination.setTotalRecordCount(totalRecordCount);
        log.info("totalRecordCount = " + totalRecordCount);

        model.addAttribute("pagination", pagination);
        log.info("pagination = " + pagination);

        model.addAttribute("list", boardService.getStudyBoardList(pagination));
        log.info("list = " + boardService.getStudyBoardList(pagination));

        model.addAttribute("boardWriteDto", boardWriteDto);

        return "/index";
    }
}
