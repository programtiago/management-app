package com.netceed.management.management_app.user;

import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.equipment.EquipmentDto;
import com.netceed.management.management_app.entity.equipment.EquipmentMapper;
import com.netceed.management.management_app.entity.user.*;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipmentDto;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipmentMapper;
import com.netceed.management.management_app.exception.BirthayDateException;
import com.netceed.management.management_app.exception.EmailAlreadyExistsException;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.EquipmentRepository;
import com.netceed.management.management_app.repository.UserEquipmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.EquipmentService;
import com.netceed.management.management_app.service.UserEquipmentService;
import com.netceed.management.management_app.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private UserEquipmentService userEquipmentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private UserEquipmentRepository userEquipmentRepository;
    private User testUser1;
    private User testUser2;
    private Equipment testEquipment1;
    private Equipment testEquipment2;
    private User nonExistentTestUserOnTheList;
    private final List<UserDto> usersDtoList = new ArrayList<>();
    private UserMapper userMapper = new UserMapper();
    private EquipmentMapper equipmentMapper = new EquipmentMapper();
    private UserEquipmentMapper userEquipmentMapper = new UserEquipmentMapper();

    @BeforeEach
    void setUp(){}

    @BeforeAll
    void initialize(){
        equipmentRepository.deleteAll();
        userRepository.deleteAll();

        testUser1 = new User(1L, "Tiago", "Silva", 80056, LocalDate.of(1996, 05, 02), "223035556", WorkStatus.AVAILABLE,
                "Adeco", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2024, 05, 12), true,
                UserRole.ADMIN, "programtiago@gmail.com", "912341245", "tiago123", false, null, null, false, null);

        testUser2 = new User(2L, "Elaine", "Cruz", 30038, LocalDate.of(1998, 06, 16), "448035556", WorkStatus.AVAILABLE, "Adeco", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2024, 05, 12), true,
                UserRole.EMPLOYEE, "elaine.cruz@gmail.com", "965214523", "elaine123", false, null, null, false , null);

        nonExistentTestUserOnTheList = new User(3L, "Rui", "Salgado", 60054, LocalDate.of(1987, 04, 16), "448025530", WorkStatus.AVAILABLE, "Adeco",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2023, 10, 30), true, UserRole.EMPLOYEE, "rui.salgado@gmail.com", "965214536", "rui123", false, null, null, false, null);

        testEquipment1 = new Equipment("Monitor XXL", "asfasd231321321213", "BRANDY", "MODELX", "Monitors", "Development");

        testEquipment2 = new Equipment("Rato Microsoft", "M213412X", "Microsoft", "M1254", "Mouse", "Development");

        userRepository.save(testUser1);
        userRepository.save(testUser2);
        userRepository.save(nonExistentTestUserOnTheList);

        equipmentRepository.save(testEquipment1);
        equipmentRepository.save(testEquipment2);

        List<User> usersList = Arrays.asList(testUser1, testUser2);
        usersDtoList.addAll(userMapper.convertListUserToDto(usersList));
    }

    /*
    @Test
    void testGetAllUsers() throws Exception {
        List<UserDto> users = userService.getAllUsers();

        org.assertj.core.api.Assertions.assertThat(users).isNotEmpty();
        org.assertj.core.api.Assertions.assertThat(users).hasSize(3);
        org.assertj.core.api.Assertions.assertThat(users.get(0).firstName()).isEqualTo("Tiago");
        org.assertj.core.api.Assertions.assertThat(users.get(1).firstName()).isEqualTo("Elaine");
        org.assertj.core.api.Assertions.assertThat(users.get(2).firstName()).isEqualTo("Rui");
    }
     */

    @Test
    void testCreateUserWithNoAssign() throws Exception{
        // Create User
        UserDto userDto = new UserDto(4L, "Fernando", "Castro", 80032, LocalDate.of(1996, 05, 02), WorkStatus.AVAILABLE, "INTERN", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2022, 01, 23), true, UserRole.ADMIN, "fernando.castro@gmail.com", "754035557", "965217898", "fernando123", false, null, null, null, false);

        UserDto createdUserDto = userService.create(userDto);

        org.assertj.core.api.Assertions.assertThat(createdUserDto.id()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(userDto.firstName()).isEqualTo(createdUserDto.firstName());
        org.assertj.core.api.Assertions.assertThat(userDto.email()).isEqualTo(createdUserDto.email());

        //Verify if the user is saved on database
        User savedUser = userRepository.findById(createdUserDto.id()).orElseThrow(() -> new ResourceNotFoundException("No resource found with the id " + createdUserDto.id()));
        org.assertj.core.api.Assertions.assertThat(savedUser).isNotNull();
        org.assertj.core.api.Assertions.assertThat(userDto.email()).isEqualTo(savedUser.getEmail());
    }
    @Test
    void testCreationOfUserWithAssignmentEquipment() throws BadRequestException {

        UserDto userDto = new UserDto(7L, "António", "Amadeu", 60032, LocalDate.of(1965, 3, 23), WorkStatus.AVAILABLE, "INTERN", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2021, 11, 12),
                true, UserRole.EMPLOYEE, "antonio.amadeu@gmail.com",  "244035541", "915302412", "antonio123", false, null, null, null, false);

        UserEquipmentDto userEquipmentDto = userService.createUserForEquipment(userDto, testEquipment1.getId());

        Equipment equipment = equipmentRepository.findById(testEquipment1.getId()).orElseThrow();
        EquipmentDto equipmentDto = equipmentMapper.toDto(equipment);

        org.assertj.core.api.Assertions.assertThat(userEquipmentDto.id()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(userDto.firstName()).isEqualTo(userEquipmentDto.user().getFirstName());
        org.assertj.core.api.Assertions.assertThat(userDto.email()).isEqualTo(userEquipmentDto.user().getEmail());

        //Verify if the user is saved on database
        User savedUser = userRepository.findById(userEquipmentDto.user().getId()).orElseThrow(() -> new ResourceNotFoundException("No resource found with the id " + userEquipmentDto.user().getId()));
        org.assertj.core.api.Assertions.assertThat(savedUser).isNotNull();
        org.assertj.core.api.Assertions.assertThat(userDto.email()).isEqualTo(savedUser.getEmail());
    }


    @Test
    void testExistsByWorkNumber_WhenExists_ReturnsTrue() {
        int existingWorkNumber = 30032;
        boolean exists = userService.checkIfGivenWorkNumberExists(existingWorkNumber);
        org.assertj.core.api.Assertions.assertThat(exists).isTrue();
    }

    @Test
    void testExistsByWorkNumber_WhenNotExists_ReturnsFalse() {
        int nonExistingWorkNumber = 84125;
        boolean notExists = userService.checkIfGivenWorkNumberExists(nonExistingWorkNumber);
        org.assertj.core.api.Assertions.assertThat(notExists).isFalse();
    }

    @Test
    void testExistsByEmail_WhenNotExists_ReturnsTrue(){
        String emailExists = "programtiago@gmail.com";

        boolean exists = userService.findEmailIfAlreadyExists(emailExists);
        org.assertj.core.api.Assertions.assertThat(exists).isTrue();
    }

    @Test
    void testExistsByEmail_WhenNotExists_ReturnsFalse(){
        String emailExists = "afonso.martins@gmail.com";

        boolean notExists = userService.findEmailIfAlreadyExists(emailExists);
        org.assertj.core.api.Assertions.assertThat(notExists).isFalse();
    }

    @Test
    void testGetUserById() {
        UserDto foundUser = userService.getById(testUser2.getId());

        org.assertj.core.api.Assertions.assertThat(testUser2.getFirstName()).isEqualTo(foundUser.firstName());
        org.assertj.core.api.Assertions.assertThat(testUser2.getLastName()).isEqualTo(foundUser.lastName());
        org.assertj.core.api.Assertions.assertThat(testUser2.getEmail()).isEqualTo(foundUser.email());
    }

    @Test
    void testUpdateUser(){
        testUser2.setActive(false);
        testUser2.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        testUser2.setWorkStatus(WorkStatus.VACATION);
        testUser2.setAvailableForVacation(true);

        UserDto userToUpdateDto = userMapper.toDto(testUser2);
        User userUpdated = userService.update(userToUpdateDto, userToUpdateDto.id());

        org.assertj.core.api.Assertions.assertThat(userUpdated.isActive()).isFalse();
        org.assertj.core.api.Assertions.assertThat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).isEqualTo(userUpdated.getUpdatedAt());
        org.assertj.core.api.Assertions.assertThat(WorkStatus.VACATION).isEqualTo(userUpdated.getWorkStatus());
        org.assertj.core.api.Assertions.assertThat(userUpdated.isAvailableForVacation()).isTrue();
    }

    @Test
    void testDeleteUser(){
        userService.delete(nonExistentTestUserOnTheList.getId());

        Optional<User> deletedUser = userRepository.findById(nonExistentTestUserOnTheList.getId());
        org.assertj.core.api.Assertions.assertThat(deletedUser).isEmpty();
    }

    @Test
    void testDifferentObjects(){
        org.assertj.core.api.Assertions.assertThat(testUser1).isNotEqualTo(testUser2);
    }

    @Test
    void testSizeCollectionAndObjectsExistentOrNot(){
        org.assertj.core.api.Assertions.assertThat(usersDtoList)
                .hasSize(2)
                .contains(userMapper.toDto(testUser1), userMapper.toDto(testUser1))
                .doesNotContain(userMapper.toDto(nonExistentTestUserOnTheList));
    }

    @Test
    void shouldThrowExceptionWhenAgeIsLessThan18(){
        LocalDate underageBirthday = LocalDate.now().minusYears(17).plusDays(1); // 17 years and 1 day old

        UserDto userDto = new UserDto(5L, "sadas", "asdasdas", 80030, underageBirthday, WorkStatus.AVAILABLE, "INTERN", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2022, 01, 23),
                true, UserRole.ADMIN, "fernado.silva@gmail.com", "745035582", "965217898", "fernando123", false, null, null, null, false);


        BirthayDateException throwable = Assertions.catchThrowableOfType(() -> userService.create(userDto), BirthayDateException.class);

        Assertions.assertThat(throwable)
                .isInstanceOf(BirthayDateException.class)
                .hasMessage("Birthday Date given indicate the user doesn't have 18 years or more");
    }

    @Test
    void shouldNotThrowExceptionWhenAgeIs18() {
        LocalDate validBirthday = LocalDate.now().minusYears(18); // Exactly 18 years old

        // This should not throw an exception
        userService.checkIfGivenBirthdayDateIsValid(validBirthday);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists(){
        UserDto userDto = new UserDto(5L, "sadas", "asdasdas", 80030, LocalDate.of(1997, 2, 12), null, "INTERN", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2024, 1, 2), true, UserRole.EMPLOYEE, "320335541", "965217898", "asfdasds", null, false, null, null, null, false);


        EmailAlreadyExistsException throwable = Assertions.catchThrowableOfType(() -> userService.create(userDto), EmailAlreadyExistsException.class);

        Assertions.assertThat(throwable)
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessage("Email " +  userDto.email() + " already registered");
    }
    @Test
    void shouldNotThrowExceptionWhenEmailDoesNotExists() {
        String testEmail = "xxxxxxxx@x.com";

        // This should not throw an exception
       userService.findEmailIfAlreadyExists(testEmail);
    }

    @Test
    void shouldThrowExceptionWhenWorkNumberAlreadyExists(){
        UserDto userDto = new UserDto(5L, "xxxxx", "yyyyy", 80054, LocalDate.of(2000, 9, 30), WorkStatus.AVAILABLE, "ADDECO", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2024, 1, 2),
                true, UserRole.ADMIN, "yyyyyyyyyyyyyyy123@gmail.com", "550335522", "912317421", "teste123xx", false, null, null, null, false);

        IllegalArgumentException throwable = Assertions.catchThrowableOfType(() -> userService.create(userDto), IllegalArgumentException.class);

        Assertions.assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Work Number " +  userDto.workNumber() + " already registered");
    }
    @Test
    void shouldNotThrowExceptionWhenWorkNumberDoesNotExists() {
        int testWorkNumber = 32052;

        // This should not throw an exception
        userService.checkIfGivenWorkNumberExists(testWorkNumber);
    }

    /******************* workNumber < 30000 **************************/
    /*
    @Test
    void shouldThrowExceptionWhenWorkNumberIsLessThanThirtyThousand() {
        UserDto userDto = new UserDto(5L, "Catarina", "Almeida", 29999, LocalDate.of(1989, 10, 10), WorkStatus.AVAILABLE,
                null, null, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2023, 5, 27),
                true, UserRole.ADMIN, "yyyyyyyysadjpoasjdasyyyyyyy123@gmail.com", "330435521", "912365419", "safasdoajsdia", false, null, null, null);

        ConstraintViolationException throwable = Assertions.catchThrowableOfType(() -> userService.create(userDto), ConstraintViolationException.class);

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class);

    }

    /******************* workNumber > 100000 **************************/
    @Test
    void shouldThrowExceptionWhenWorkNumberIsGreaterThanHundredThousand() {
        UserDto userDto = new UserDto(5L, "Catarina", "Almeida", 100001, LocalDate.of(1989, 10, 10), WorkStatus.AVAILABLE, "INTERN", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2023, 5, 27),
                true, UserRole.ADMIN, "yyyyyyyysadjpoasjdasyyyyyyy123@gmail.com", "442254855", "912365419", "safasdoajsdia", false, null, null, null, false);

        ConstraintViolationException throwable = Assertions.catchThrowableOfType(() -> userService.create(userDto), ConstraintViolationException.class);

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class);

    }

    @Test
    void testActivateUser(){
        UserDto activatedUser = userService.activateAccount(nonExistentTestUserOnTheList.getId());

        org.assertj.core.api.Assertions.assertThat(activatedUser.isActive()).isEqualTo(true);

    }

    /******************* Try active user object with isActive: true **************************/
    @Test
    void shouldThrowExceptionWhenTryingActivateUserThatIsAlreadyActivated() {
        IllegalArgumentException throwable = Assertions.catchThrowableOfType(() -> userService.activateAccount(testUser1.getId()), IllegalArgumentException.class);

        Assertions.assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User already activated. Impossible to active with id " + testUser1.getId());

    }

    /******************* Try deactivate user object with isActive: false **************************/
    @Test
    void shouldThrowExceptionWhenTryingDeactivateUserThatIsAlreadyDeactivated() {
        IllegalArgumentException throwable = Assertions.catchThrowableOfType(() -> userService.deactivateAccount(nonExistentTestUserOnTheList.getId()), IllegalArgumentException.class);

        Assertions.assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User already deactivated. Impossible to active with id " + nonExistentTestUserOnTheList.getId());

    }

}
