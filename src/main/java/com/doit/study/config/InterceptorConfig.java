package com.doit.study.config;

import com.doit.study.alarm.service.AlarmService;
import com.doit.study.interceptor.JoinCheckInterceptor;
import com.doit.study.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    AlarmService alarmService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(alarmService))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/join", "/login", "/logout", "/kakao/**", "/naver/**", "/favicon.ico", "health", 
                        "/board/result/**", "/css/**", "/img/**", "/js/**", "/join/**", "/login/**", "/error", "/board/list/**");

        registry.addInterceptor(new JoinCheckInterceptor())
                .order(2)
                .addPathPatterns("/join");
    }
}
