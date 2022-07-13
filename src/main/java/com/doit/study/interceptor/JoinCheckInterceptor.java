package com.doit.study.interceptor;

import com.doit.study.member.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class JoinCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session != null &&
                (session.getAttribute(SessionConst.LOGIN_MEMBER) != null ||
                 session.getAttribute(SessionConst.NAVER_MEMBER) != null ||
                 session.getAttribute(SessionConst.KAKAO_MEMBER) != null)) {
            log.info("로그인 회원의 잘못된 접근");
            //로그아웃으로 redirect
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
