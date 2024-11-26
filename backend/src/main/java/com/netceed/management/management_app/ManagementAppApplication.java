package com.netceed.management.management_app;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.enums.ShiftType;
import com.netceed.management.management_app.enums.UserRole;
import com.netceed.management.management_app.enums.WorkStatus;
import com.netceed.management.management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

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

		User user = new User("Tiago", "Silva", "programtiago@gmail.com", 30035, LocalDate.of(1996,5,2),
				"tiago123", LocalDate.now(), "IT", UserRole.EMPLOYEE, ShiftType.MORNING, "INTERN",
				"913562547");

		userRepository.save(user);

		 */
	}
}
