package com.netceed.management.management_app;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.enums.UserRole;
import com.netceed.management.management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ManagementAppApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ManagementAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		User user = new User("Tiago", "Silva", "programtiago@gmail.com", "teste123", UserRole.EMPLOYEE);
		userRepository.save(user);

		 */
	}
}
