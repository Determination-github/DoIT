package com.doit.study;

import com.doit.study.config.EmailConfig;
import com.doit.study.config.S3Config;
import com.doit.study.config.SecurityConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

@MapperScan(value = {"com.doit.study.mapper"})
@PropertySource(value = {"classpath:database_pwd.properties", "classpath:database_id.properties"})
@SpringBootApplication
public class StudyApplication {

	public static void main(String[] args) {

		SpringApplication.run(StudyApplication.class, args);
		ApplicationContext context = new AnnotationConfigApplicationContext(EmailConfig.class, S3Config.class, SecurityConfig.class);
	}

}