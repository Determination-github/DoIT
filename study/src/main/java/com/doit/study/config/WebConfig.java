package com.doit.study.config;

import com.doit.study.member.naver.NaverLoginBO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    NaverLoginBO naverLoginBO() {
        return new NaverLoginBO();
    }

}
