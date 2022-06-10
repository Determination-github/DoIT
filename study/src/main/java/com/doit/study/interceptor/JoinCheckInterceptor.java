package com.doit.study.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class JoinCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false);

        if (    session != null ||
                session.getAttribute("LoginDto") != null
        ) {
            log.info("로그인 회원의 잘못된 접근");
            //로그아웃으로 redirect
            response.sendRedirect("/?redirectURL=" + requestURI);
            return false;
        }

        return true;
    }
}
