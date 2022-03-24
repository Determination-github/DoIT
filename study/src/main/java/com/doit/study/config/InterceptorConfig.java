package com.doit.study.config;

import com.doit.study.interceptor.JoinCheckInterceptor;
import com.doit.study.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/join", "/index",
                        "/css/**", "/*.ico", "/error");

        registry.addInterceptor(new JoinCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/login", "/index",
                        "/css/**", "/*.ico", "/error");
    }
}
