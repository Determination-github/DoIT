package com.doit.study;


import com.doit.study.alarm.dto.AlarmDto;
import com.doit.study.alarm.service.AlarmService;
import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.MemberDto;

import com.doit.study.board.domain.Pagination;
import com.doit.study.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final AlarmService alarmService;

    /**
     * 홈 컨트롤러
     * @param request
     * @param currentPage
     * @param pageSize
     * @param model
     * @return String
     * @throws Exception
     */
    @GetMapping
    public String home(
            HttpServletRequest request,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize,
            Model model) throws Exception {

        //세션값 세팅
        HttpSession session = request.getSession(false);

        Integer id = null;
        String nickName;
        String path;

        if(session!=null) {

            MemberDto naverDto = (MemberDto) session.getAttribute(SessionConst.NAVER_MEMBER);
            MemberDto kakaoDto = (MemberDto) session.getAttribute(SessionConst.KAKAO_MEMBER);
            MemberDto memberDto = (MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

            if (naverDto != null) {
                id = naverDto.getId();
                nickName = naverDto.getNickname();
                setSessionInfo(session, id, nickName);
                //알람 가져오기
                getAlarm(session, id);
            } else if (kakaoDto != null) {
                id = kakaoDto.getId();
                nickName = kakaoDto.getNickname();
                setSessionInfo(session, id, nickName);
                //알람 가져오기
                getAlarm(session, id);
            } else if (memberDto != null) {
                id = memberDto.getId();
                nickName = memberDto.getNickname();
                setSessionInfo(session, id, nickName);
                //알람 가져오기
                getAlarm(session, id);
            }
        }

        Integer totalRecordCount = boardService.getCount();
        if(totalRecordCount != null) {

            //페이징 처리
            Pagination pagination = new Pagination(currentPage, pageSize);
            pagination.setTotalRecordCount(totalRecordCount);
            model.addAttribute("pagination", pagination);

            model.addAttribute("list", boardService.getStudyBoardList(id, pagination));
        }

        return "index";
    }

    //---------------------------------------------extracted Method------------------------------

    //세션값 저장
    private void setSessionInfo(HttpSession session, Integer id, String nickName) {
        session.setAttribute("id", id);
        session.setAttribute("nickName", nickName);
    }

    //알람정보 가져오기
    private void getAlarm(HttpSession session, Integer id) throws Exception {
        session.removeAttribute("alarmList");
        List<AlarmDto> alarm = alarmService.getAlarm(id);
        if(alarm.isEmpty()) {
            session.setAttribute("alarmList", null);
        } else {
            session.setAttribute("alarmList", alarm);
        }
    }
}
