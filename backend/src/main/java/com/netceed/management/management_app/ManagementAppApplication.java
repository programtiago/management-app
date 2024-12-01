package com.netceed.management.management_app;

import com.netceed.management.management_app.entity.Department;
import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.Shift;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.enums.StatusEquipment;
import com.netceed.management.management_app.enums.UserRole;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.repository.EquipmentRepository;
import com.netceed.management.management_app.repository.ShiftRepository;
import com.netceed.management.management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@SpringBootApplication
@RequiredArgsConstructor
public class ManagementAppApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final ShiftRepository shiftRepository;
	private final DepartmentRepository departmentRepository;
	private final EquipmentRepository equipmentRepository;


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
		scanner.setGoal("Housing");
		scanner.setUnity("Refurb");
		scanner.setType("Scanners");
		scanner.setModel("DS2208");
		scanner.setMacAddress("");
		scanner.setBrand("Zebra");
		scanner.setStatusPhysic("New");
		scanner.setStatusEquipment(StatusEquipment.NOT_AVAILABLE);
		scanner.setAllocationDateTime(LocalDateTime.of(2024, 11, 29, 18, 35));
		scanner.setReturningDateTime(LocalDateTime.now());

		Set<Equipment> scannerEquipment = new HashSet<>();
		scannerEquipment.add(scanner);

		//user.setEquipments(scannerEquipment);
		equipmentRepository.saveAll(scannerEquipment);
		userRepository.saveAll(scannerUsers);

		Equipment printer = new Equipment();

		printer.setDescription("Impressora Toshiba B-FV4T-TS14-QM-R");
		printer.setSerialNumber("2302P000862");
		printer.setLocation("Packaging NB6");
		printer.setGoal("Packaging");
		printer.setUnity("Refurb");
		printer.setType("Printers");
		printer.setModel("B-FV4T");
		printer.setMacAddress("");
		printer.setBrand("Toshiba");
		printer.setStatusPhysic("New");
		printer.setStatusEquipment(StatusEquipment.NOT_AVAILABLE);
		printer.setAllocationDateTime(LocalDateTime.of(2024, 11, 29, 17, 32));
		printer.setReturningDateTime(LocalDateTime.now());

		Set<Equipment> printerEquipment = new HashSet<>();
		printerEquipment.add(printer);

		//user2.setEquipments(scannerEquipment);
		equipmentRepository.saveAll(scannerEquipment);
		userRepository.saveAll(printerUsers);

		Equipment screen = new Equipment();

		screen.setDescription("Monitor Philips 222B1TC/00");
		screen.setSerialNumber("22180010552203");
		screen.setLocation("Housing RCU 1");
		screen.setGoal("Housing");
		screen.setUnity("Refurb");
		screen.setType("Screens");
		screen.setModel("222B1TC/00");
		screen.setMacAddress("");
		screen.setBrand("Philips");
		screen.setStatusPhysic("New");
		screen.setStatusEquipment(StatusEquipment.NOT_AVAILABLE);
		screen.setAllocationDateTime(LocalDateTime.of(2024, 11, 29, 17, 56));
		screen.setReturningDateTime(LocalDateTime.now());

		Set<Equipment> screenEquipment = new HashSet<>();
		screenEquipment.add(screen);

		//user.setEquipments(screenEquipment);
		equipmentRepository.saveAll(screenEquipment);
		userRepository.saveAll(screenUsers);

		Equipment wifiPen = new Equipment();

		wifiPen.setDescription("Pen WIFI TP-Link AC1300 UM-MIMO");
		wifiPen.setSerialNumber("RF1K115000774");
		wifiPen.setLocation("Development 1.2");
		wifiPen.setGoal("Development");
		wifiPen.setUnity("Refurb");
		wifiPen.setType("USB Adapter");
		wifiPen.setModel("AC1300");
		wifiPen.setMacAddress("");
		wifiPen.setBrand("TP-link");
		wifiPen.setStatusPhysic("New");
		wifiPen.setStatusEquipment(StatusEquipment.NOT_AVAILABLE);
		wifiPen.setAllocationDateTime(LocalDateTime.of(2024, 11, 29, 17, 21));
		wifiPen.setReturningDateTime(LocalDateTime.now());

		Set<Equipment> wifiPenEquipement = new HashSet<>();
		wifiPenEquipement.add(wifiPen);

		//user4.setEquipments(wifiPenEquipement);
		equipmentRepository.saveAll(wifiPenEquipement);
		userRepository.saveAll(usbAdapterUsers);

		Equipment desktop = new Equipment();

		desktop.setDescription("Desktop Dell OPT3040MFF-SB16-R6");
		desktop.setSerialNumber("22180010552203");
		desktop.setLocation("Housing RCU 1");
		desktop.setGoal("Housing");
		desktop.setUnity("Refurb");
		desktop.setType("Desktops");
		desktop.setModel("OPT3040MFF-SB16-R6");
		desktop.setMacAddress("");
		desktop.setBrand("Dell");
		desktop.setStatusPhysic("Used");
		desktop.setStatusEquipment(StatusEquipment.NOT_AVAILABLE);
		desktop.setAllocationDateTime(LocalDateTime.of(2024, 11, 29, 17, 13));
		desktop.setReturningDateTime(LocalDateTime.now());

		Set<Equipment> desktopEquipment = new HashSet<>();
		desktopEquipment.add(desktop);

		//user2.setEquipments(desktopEquipment);
		equipmentRepository.saveAll(desktopEquipment);
		userRepository.saveAll(desktopUsers);

		 */
	}
}
