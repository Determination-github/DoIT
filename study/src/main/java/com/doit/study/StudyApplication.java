package com.doit.study;


import com.doit.study.config.EmailConfig;
import com.doit.study.config.WebConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@MapperScan(value = {"com.doit.study.mapper"})
@SpringBootApplication
public class StudyApplication {

	public static void main(String[] args) {

		SpringApplication.run(StudyApplication.class, args);
		ApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class, EmailConfig.class);
	}

}
