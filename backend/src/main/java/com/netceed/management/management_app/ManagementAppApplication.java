package com.netceed.management.management_app;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@RequiredArgsConstructor
@EnableWebMvc
public class ManagementAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(ManagementAppApplication.class, args);
	}
}
