package com.netceed.management.management_app;

import com.netceed.management.management_app.entity.Department;
import com.netceed.management.management_app.entity.Shift;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.enums.ShiftType;
import com.netceed.management.management_app.enums.UserRole;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.repository.ShiftRepository;
import com.netceed.management.management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@RequiredArgsConstructor
public class ManagementAppApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final ShiftRepository shiftRepository;
	private final DepartmentRepository departmentRepository;


	public static void main(String[] args) {
		SpringApplication.run(ManagementAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*

		Shift generalShift = new Shift("General", LocalTime.of(9,0,0), LocalTime.of(18,0, 0), true);
		Shift morningShift = new Shift("Morning", LocalTime.of(7,0,0), LocalTime.of(15,30, 0), true);
		Shift afternoonShift = new Shift("Afternoon", LocalTime.of(15,30,0), LocalTime.of(0,0, 0), true);
		Shift nightShift = new Shift("Night", LocalTime.of(0,0,0), LocalTime.of(7,0, 0), true);

		List<Shift> shifts = new ArrayList<>(Arrays.asList(generalShift, morningShift, afternoonShift, nightShift));

		shiftRepository.saveAll(shifts);

		 */

		/*

		User user = new User("Tiago", "Silva", "programtiago@gmail.com", 30035, LocalDate.of(1996,5,2),
				"tiago123", LocalDate.now(), "IT", UserRole.EMPLOYEE, morningShift, "INTERN",
				"913562547");

		userRepository.save(user);

		Department itDepartment = new Department("IT", "Information Technology", 0);
		Department logisticDepartment = new Department("LOG", "Logistic", 0);
		Department qualityControlDepartment = new Department("QC", "Quality Control", 0);
		Department purchasingDepartment = new Department("PCHG", "Purchasing", 0);
		Department humanResourcesDepartment = new Department("HR", "Human Resources", 0);
		Department developmentDepartment = new Department("DVL", "Development", 0);
		Department administrationDepartment = new Department("ADMIN", "Administration", 0);
		Department productionDepartment = new Department("PROD", "Administration", 0);

		List<Department> departments = new ArrayList<>(Arrays.asList(itDepartment, logisticDepartment, qualityControlDepartment, purchasingDepartment, humanResourcesDepartment, developmentDepartment,
				administrationDepartment, productionDepartment));

		departmentRepository.saveAll(departments);


		 */

	}
}
