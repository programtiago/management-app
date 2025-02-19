package com.netceed.management.management_app;

import com.netceed.management.management_app.entity.building.Building;
import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.equipment.StatusEquipment;
import com.netceed.management.management_app.entity.shift.Shift;
import com.netceed.management.management_app.entity.user.User;
import com.netceed.management.management_app.entity.user.UserRole;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipment;
import com.netceed.management.management_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
@EnableWebMvc
@EnableAspectJAutoProxy
public class ManagementAppApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final ShiftRepository shiftRepository;
	private final DepartmentRepository departmentRepository;
	private final EquipmentRepository equipmentRepository;
	private final BuildingRepository buildingRepository;

	public static void main(String[] args) {
		SpringApplication.run(ManagementAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Building buildingA = new Building();

		buildingA.setDescription("BUILDING A");
		buildingA.setActive(true);
		buildingA.setRegistryDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

		Building buildingB = new Building();

		buildingB.setDescription("BUILDING B");
		buildingB.setActive(true);
		buildingB.setRegistryDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

		buildingRepository.save(buildingA);
		buildingRepository.save(buildingB);

		Shift generalShift = new Shift("General", LocalTime.of(9,0,0), LocalTime.of(18,0, 0), true);
		Shift morningShift = new Shift("Morning", LocalTime.of(7,0,0), LocalTime.of(15,30, 0), true);
		Shift afternoonShift = new Shift("Afternoon", LocalTime.of(15,30,0), LocalTime.of(0,0, 0), true);
		Shift nightShift = new Shift("Night", LocalTime.of(0,0,0), LocalTime.of(7,0, 0), true);

		List<Shift> shifts = new ArrayList<>(Arrays.asList(generalShift, morningShift, afternoonShift, nightShift));

		shiftRepository.saveAll(shifts);


		Department itDepartment = new Department("IT", "Information Technology", 0);

		Department logisticDepartment = new Department("LOG", "Logistic", 0);

		Department qualityControlDepartment = new Department("QC", "Quality Control", 0);

		Department purchasingDepartment = new Department("PCHG", "Purchasing", 0);

		Department humanResourcesDepartment = new Department("HR", "Human Resources", 0);

		Department developmentDepartment = new Department("DVL", "Development", 0);

		Department administrationDepartment = new Department("ADMIN", "Administration", 0);

		Department productionDepartment = new Department("PROD", "Production", 0);

		List<Department> departments = new ArrayList<>(Arrays.asList(itDepartment, logisticDepartment, qualityControlDepartment, purchasingDepartment, humanResourcesDepartment, developmentDepartment,
				administrationDepartment, productionDepartment));

		departmentRepository.saveAll(departments);

		User user = new User();

		user.setFirstName("Tiago");
		user.setLastName("Silva");
		user.setEmail("programtiago@gmail.com");
		user.setWorkNumber(30035);
		user.setNif("215032150");
		user.setRegistryDate(LocalDateTime.of(2025, 1, 5, 12, 20).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
		user.setBirthdayDate(LocalDate.of(1996,5,2));
		user.setPassword("tiago123");
		user.setAdmissionDate(LocalDate.now());
		user.setUserRole(UserRole.EMPLOYEE);
		user.setShift(morningShift);
		user.setRecruitmentCompany("INTERN");
		user.setContactNumber("913562547");
		user.setUpdatedAt(null);

		User user2 = new User();

		user2.setFirstName("António");
		user2.setLastName("Rodrigues");
		user2.setEmail("antonio.rodrigues@gmail.com");
		user2.setWorkNumber(85456);
		user2.setNif("523532351");
		user2.setRegistryDate(LocalDateTime.of(2025, 1, 6, 13, 45).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
		user2.setBirthdayDate(LocalDate.of(1985,10,22));
		user2.setPassword("antonio123");
		user2.setAdmissionDate(LocalDate.now());
		user2.setUserRole(UserRole.ADMIN);
		user2.setShift(generalShift);
		user2.setRecruitmentCompany("ADECO");
		user2.setContactNumber("913254123");
		user2.setUpdatedAt(null);

		User user3 = new User();

		user3.setFirstName("Maria");
		user3.setLastName("Antunes");
		user3.setEmail("maria.antunes@gmail.com");
		user3.setWorkNumber(80037);
		user3.setNif("328132302");
		user3.setRegistryDate(LocalDateTime.of(2025, 1, 8, 17, 5).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
		user3.setBirthdayDate(LocalDate.of(1991,2,12));
		user3.setPassword("maria123");
		user3.setAdmissionDate(LocalDate.now());
		user3.setUserRole(UserRole.EMPLOYEE);
		user3.setShift(afternoonShift);
		user3.setRecruitmentCompany("SYNERGIE");
		user3.setContactNumber("965214655");
		user3.setUpdatedAt(null);

		User user4 = new User();

		user4.setFirstName("Daniel");
		user4.setLastName("Bastos");
		user4.setEmail("daniel.bastos@gmail.com");
		user4.setWorkNumber(85231);
		user4.setNif("223532810");
		user4.setRegistryDate(LocalDateTime.of(2025, 1, 4, 11, 27).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
		user4.setBirthdayDate(LocalDate.of(1998,1,1));
		user4.setPassword("daniel123");
		user4.setAdmissionDate(LocalDate.now());
		user4.setUserRole(UserRole.EMPLOYEE);
		user4.setShift(nightShift);
		user4.setRecruitmentCompany("RANDSTAD");
		user4.setContactNumber("915236214");
		user4.setUpdatedAt(null);

		Set<User> scannerUsers = new HashSet<>();
		Set<User> printerUsers = new HashSet<>();
		Set<User> screenUsers = new HashSet<>();
		Set<User> desktopUsers = new HashSet<>();
		Set<User> usbAdapterUsers = new HashSet<>();

		scannerUsers.add(user3);
		printerUsers.add(user2);
		screenUsers.add(user);
		usbAdapterUsers.add(user4);
		desktopUsers.add(user2);

		userRepository.saveAll(printerUsers);
		userRepository.saveAll(screenUsers);
		userRepository.saveAll(usbAdapterUsers);
		userRepository.saveAll(desktopUsers);

		Equipment scanner = new Equipment();

		scanner.setDescription("Scanner Zebra DS2208-SR7U2100SGW BLACK");
		scanner.setSerialNumber("22180010552203");
		scanner.setLocation("Housing RCU 1");
		scanner.setLocation("Housing");
		scanner.setUnity("Refurb");
		scanner.setType("Scanners");
		scanner.setModel("DS2208");
		scanner.setBrand("Zebra");
		scanner.setStatusPhysic("New");
		scanner.setStatusEquipment(StatusEquipment.AVAILABLE);
		scanner.setActive(true);
		scanner.setRegistryDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

		Set<Equipment> scannerEquipment = new HashSet<>();
		scannerEquipment.add(scanner);

		equipmentRepository.saveAll(scannerEquipment);
		userRepository.saveAll(scannerUsers);

		Equipment printer = new Equipment();

		printer.setDescription("Impressora Toshiba B-FV4T-TS14-QM-R");
		printer.setSerialNumber("2302P000862");
		printer.setLocation("Packaging NB6");
		printer.setLocation("Packaging");
		printer.setUnity("Refurb");
		printer.setType("Printers");
		printer.setModel("B-FV4T");
		printer.setBrand("Toshiba");
		printer.setStatusPhysic("New");
		printer.setStatusEquipment(StatusEquipment.AVAILABLE);
		printer.setActive(true);
		printer.setRegistryDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

		Set<Equipment> printerEquipment = new HashSet<>();
		printerEquipment.add(printer);

		equipmentRepository.saveAll(scannerEquipment);
		userRepository.saveAll(printerUsers);

		Equipment screen = new Equipment();

		screen.setDescription("Monitor Philips 222B1TC/00");
		screen.setSerialNumber("22180010552203");
		screen.setLocation("Housing RCU 1");
		screen.setLocation("Housing");
		screen.setUnity("Refurb");
		screen.setType("Screens");
		screen.setModel("222B1TC/00");
		screen.setBrand("Philips");
		screen.setStatusPhysic("New");
		screen.setStatusEquipment(StatusEquipment.AVAILABLE);
		screen.setActive(true);
		screen.setRegistryDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

		Set<Equipment> screenEquipment = new HashSet<>();
		screenEquipment.add(screen);

		equipmentRepository.saveAll(screenEquipment);
		userRepository.saveAll(screenUsers);

		Equipment wifiPen = new Equipment();

		wifiPen.setDescription("Pen WIFI TP-Link AC1300 UM-MIMO");
		wifiPen.setSerialNumber("RF1K115000774");
		wifiPen.setLocation("Development 1.2");
		wifiPen.setLocation("Development");
		wifiPen.setUnity("Refurb");
		wifiPen.setType("USB Adapter");
		wifiPen.setModel("AC1300");
		wifiPen.setBrand("TP-link");
		wifiPen.setStatusPhysic("New");
		wifiPen.setStatusEquipment(StatusEquipment.AVAILABLE);
		wifiPen.setActive(true);
		wifiPen.setRegistryDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

		Set<Equipment> wifiPenEquipement = new HashSet<>();
		wifiPenEquipement.add(wifiPen);

		equipmentRepository.saveAll(wifiPenEquipement);
		userRepository.saveAll(usbAdapterUsers);

		Equipment desktop = new Equipment();

		desktop.setDescription("Desktop Dell OPT3040MFF-SB16-R6");
		desktop.setSerialNumber("22180010552203");
		desktop.setLocation("Housing RCU 1");
		desktop.setLocation("Housing");
		desktop.setUnity("Refurb");
		desktop.setType("Desktops");
		desktop.setModel("OPT3040MFF-SB16-R6");
		desktop.setBrand("Dell");
		desktop.setStatusPhysic("Used");
		desktop.setStatusEquipment(StatusEquipment.AVAILABLE);
		desktop.setActive(true);
		desktop.setRegistryDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));


		Set<Equipment> desktopEquipment = new HashSet<>();
		desktopEquipment.add(desktop);

		equipmentRepository.saveAll(desktopEquipment);

		Set<UserEquipment> setUserEquipment1 = new HashSet<>();

		UserEquipment userEquipment1 = new UserEquipment();

		userEquipment1.setEquipment(scanner);
		userEquipment1.setUser(user2);
		userEquipment1.setAssignedDateTime(LocalDateTime.of(12,12,30, 12, 30));
		userEquipment1.setComments("First equipment assignment");
		setUserEquipment1.add(userEquipment1);

		//scanner.setUserEquipments(setUserEquipment1);

		userRepository.saveAll(desktopUsers);
	}
}
