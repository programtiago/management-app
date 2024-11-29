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

		Department itDepartment = new Department();
		itDepartment.setValue("IT");
		itDepartment.setDescription("Information Technology");
		itDepartment.setTotalEmployees(0);
		itDepartment.setRegistryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		Department logisticDepartment = new Department();
		logisticDepartment.setValue("LOG");
		logisticDepartment.setDescription("Logistic");
		logisticDepartment.setTotalEmployees(0);
		logisticDepartment.setRegistryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		Department qualityControlDepartment = new Department();
		qualityControlDepartment.setValue("QC");
		qualityControlDepartment.setDescription("Quality Control");
		qualityControlDepartment.setTotalEmployees(0);
		qualityControlDepartment.setRegistryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		Department purchasingDepartment = new Department();
		purchasingDepartment.setValue("PCHG");
		purchasingDepartment.setDescription("Purchasing");
		purchasingDepartment.setTotalEmployees(0);
		purchasingDepartment.setRegistryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		Department humanResourcesDepartment = new Department();
		humanResourcesDepartment.setValue("HR");
		humanResourcesDepartment.setDescription("Human Resources");
		humanResourcesDepartment.setTotalEmployees(0);
		humanResourcesDepartment.setRegistryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		Department developmentDepartment = new Department();
		developmentDepartment.setValue("DVL");
		developmentDepartment.setDescription("Development");
		developmentDepartment.setTotalEmployees(0);
		developmentDepartment.setRegistryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		Department administrationDepartment = new Department();
		administrationDepartment.setValue("ADMIN");
		administrationDepartment.setDescription("Administration");
		administrationDepartment.setTotalEmployees(0);
		administrationDepartment.setRegistryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		Department productionDepartment = new Department();
		productionDepartment.setValue("PROD");
		productionDepartment.setDescription("Production");
		productionDepartment.setTotalEmployees(0);
		productionDepartment.setRegistryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		List<Department> departments = new ArrayList<>(Arrays.asList(itDepartment, logisticDepartment, qualityControlDepartment, purchasingDepartment, humanResourcesDepartment, developmentDepartment,
				administrationDepartment, productionDepartment));

		departmentRepository.saveAll(departments);

		User user = new User();

		user.setFirstName("Tiago");
		user.setLastName("Silva");
		user.setEmail("programtiago@gmail.com");
		user.setWorkNumber(30035);
		user.setBirthdayDate(LocalDate.of(1996,5,2));
		user.setPassword("tiago123");
		user.setAdmissionDate(LocalDate.now());
		user.setUserRole(UserRole.EMPLOYEE);
		user.setShift(morningShift);
		user.setRecruitmentCompany("INTERN");
		user.setContactNumber("913562547");
		user.setDepartment(itDepartment);


		User user2 = new User();

		user2.setFirstName("António");
		user2.setLastName("Rodrigues");
		user2.setEmail("antonio.rodrigues@gmail.com");
		user2.setWorkNumber(85456);
		user2.setBirthdayDate(LocalDate.of(1985,10,22));
		user2.setPassword("antonio123");
		user2.setAdmissionDate(LocalDate.now());
		user2.setUserRole(UserRole.ADMIN);
		user2.setShift(generalShift);
		user2.setRecruitmentCompany("ADECO");
		user2.setContactNumber("913254123");
		user2.setDepartment(itDepartment);

		User user3 = new User();

		user3.setFirstName("Maria");
		user3.setLastName("Antunes");
		user3.setEmail("maria.antunes@gmail.com");
		user3.setWorkNumber(80037);
		user3.setBirthdayDate(LocalDate.of(1991,2,12));
		user3.setPassword("maria123");
		user3.setAdmissionDate(LocalDate.now());
		user3.setUserRole(UserRole.EMPLOYEE);
		user3.setShift(afternoonShift);
		user3.setRecruitmentCompany("SYNERGIE");
		user3.setContactNumber("965214655");
		user3.setDepartment(productionDepartment);

		User user4 = new User();

		user4.setFirstName("Daniel");
		user4.setLastName("Bastos");
		user4.setEmail("daniel.bastos@gmail.com");
		user4.setWorkNumber(85231);
		user4.setBirthdayDate(LocalDate.of(1998,1,1));
		user4.setPassword("daniel123");
		user4.setAdmissionDate(LocalDate.now());
		user4.setUserRole(UserRole.EMPLOYEE);
		user4.setShift(nightShift);
		user4.setRecruitmentCompany("RANDSTAD");
		user4.setContactNumber("915236214");
		user4.setDepartment(developmentDepartment);

		userRepository.save(user);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);


		//userRepository.save(user);
		//userRepository.save(user2);
		//userRepository.save(user3);
		//userRepository.save(user4);
		//userRepository.save(user5);

		//itDepartment.setUser(user);
		//qualityControlDepartment.setUser(user5);
		//productionDepartment.setUser(user3);
		//administrationDepartment.setUser(user2);
		//productionDepartment.setUser(user4);
		 */
	}
}
