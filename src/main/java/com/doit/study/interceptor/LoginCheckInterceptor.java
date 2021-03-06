package com.doit.study.interceptor;

import com.doit.study.alarm.dto.AlarmDto;
import com.doit.study.alarm.service.AlarmService;
import com.doit.study.member.SessionConst;
import com.doit.study.member.dto.MemberDto;
import com.doit.study.member.dto.SocialDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final AlarmService alarmService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false);
        log.info("session 값 = " +session);

        if (session == null ||
                (session.getAttribute(SessionConst.LOGIN_MEMBER) == null &&
                 session.getAttribute(SessionConst.NAVER_MEMBER) == null &&
                 session.getAttribute(SessionConst.KAKAO_MEMBER) == null)) {
            log.info("미인증 사용자 요청");
            //로그인으로 redirect
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession(false);

        Integer id = null;

        if(session!=null) {

            MemberDto naverDto = (MemberDto) session.getAttribute(SessionConst.NAVER_MEMBER);
            MemberDto kakaoDto = (MemberDto) session.getAttribute(SessionConst.KAKAO_MEMBER);
            MemberDto memberDto = (MemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

            if (naverDto != null) {
                id = setSession(session, naverDto);
                getAlarm(session, id);
            } else if (kakaoDto != null) {
                id = setSession(session, kakaoDto);
                getAlarm(session, id);
            } else if (memberDto != null) {
                id = setSession(session, memberDto);
                getAlarm(session, id);
            }
        }
    }

    /**
     * 세션에 정보 저장
     * @param session
     * @param memberDto
     * @return id
     */
    private Integer setSession(HttpSession session, MemberDto memberDto) {
        String nickName;
        Integer id;
        id = memberDto.getId();
        nickName = memberDto.getNickname();
        session.setAttribute("id", id);
        session.setAttribute("nickName", nickName);

        return id;
    }

    /**
     * 알람 가져오기
     * @param session
     * @param id
     * @throws Exception
     */
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
