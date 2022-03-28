package com.doit.study;


import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.KakaoDto;
import com.doit.study.member.dto.LoginDto;
import com.doit.study.member.dto.NaverDto;
import com.doit.study.member.service.MemberService;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.domain.SearchCondition;
import com.doit.study.board.dto.BoardDto;
import com.doit.study.board.dto.SearchBoardDto;
import com.doit.study.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping
//    @ResponseBody
    public String home(
            @SessionAttribute(name = SessionConst.NAVER_MEMBER, required = false) NaverDto naverDto,
            @SessionAttribute(name = SessionConst.KAKAO_MEMBER, required = false) KakaoDto kakaoDto,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginDto loginDto,
            Model model) {

        //네이버로 로그인 했는데 회원정보가 없는 경우
        if (naverDto != null) {
            String naverEmail = naverDto.getNaverEmail();
            if (memberService.findSocialMember(naverEmail) == null) {
                log.info("회원가입이 필요합니다.");
                model.addAttribute("naverMember", naverDto);
                return "redirect:/members/join";
            }
        }

        if (kakaoDto != null) {
            String kakaoEmail = kakaoDto.getKakaoEmail();
            if (kakaoEmail == null) {
                log.info("회원가입이 필요합니다. - 이메일 정보가 없음");
                model.addAttribute("kakaoMember", kakaoDto);
                return "redirect:/members/join";
            }

            if (kakaoEmail != null) {
                if (memberService.findSocialMember(kakaoEmail) == null) {
                    log.info("회원가입이 필요합니다.");
                    model.addAttribute("kakaoMember", kakaoDto);
                    return "redirect:/members/join";
                }
            }
        }

        if (loginDto == null && naverDto == null && kakaoDto == null) {
            log.info("회원정보가 없습니다.");
            return "Home";
        }

        //loginDto의 값이 있는 경우(로그인 한 경우)
        model.addAttribute("member", loginDto);
        log.info("로그인 성공");
        return "index";
    }

    @GetMapping
    public String list(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
                       Model m, HttpServletRequest request) throws Exception {
//        if(!loginCheck(request))
//            return "redirect:/login/login?toURL="+request.getRequestURL();

        BoardDto boardDto = new BoardDto();
        int totalRecordCount = boardService.getCount();
        Pagination pagination = new Pagination(currentPage, pageSize);
        pagination.setTotalRecordCount(totalRecordCount);

        m.addAttribute("pagination", pagination);
        m.addAttribute("list", boardService.getPage(pagination));
        m.addAttribute("board", boardDto);
        return "/index2";
    }
}
