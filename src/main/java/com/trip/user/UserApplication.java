package com.trip.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.trip.controller,"
		+ "com.trip.user, com.trip.dayplanner, com.example.cloudbalance")
@ImportResource({"classpath*:jaxClientConfig.xml"})
@Configuration
public class UserApplication extends SpringBootServletInitializer {
	private static final Class<UserApplication> applicationClass = UserApplication.class;
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}
}
