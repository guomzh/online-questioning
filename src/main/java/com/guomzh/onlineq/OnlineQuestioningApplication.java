package com.guomzh.onlineq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class OnlineQuestioningApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(OnlineQuestioningApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(OnlineQuestioningApplication.class, args);
	}
}
