package com.netceed.management.management_app.user;

import com.netceed.management.management_app.entity.user.*;
import com.netceed.management.management_app.exception.BirthayDateException;
import com.netceed.management.management_app.exception.EmailAlreadyExistsException;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
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
    private UserRepository userRepository;
    private User testUser1;
    private User testUser2;
    private User nonExistentTestUserOnTheList;
    private final List<UserDto> usersDtoList = new ArrayList<>();
    private UserMapper userMapper = new UserMapper();

    @BeforeEach
    void setUp(){

    }

    @BeforeAll
    void initialize(){
        userRepository.deleteAll();

        testUser1 = new User(1L, "Tiago", "Silva", 30032, LocalDate.of(1996, 05, 02), null, WorkStatus.AVAILABLE,
                null, "Adeco", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2024, 05, 12), true,
                UserRole.ADMIN, "programtiago@gmail.com", "912341245", "tiago123", null, null);

        testUser2 = new User(2L, "Elaine", "Cruz", 80052, LocalDate.of(1998, 06, 16), null, WorkStatus.AVAILABLE,
                null, "Adeco", "30-10-2023 12:45", LocalDate.of(2023, 10, 30), true,
                UserRole.EMPLOYEE, "elaine.cruz@gmail.com", "965214523", "elaine123", null, null);

        nonExistentTestUserOnTheList = new User(3L, "Rui", "Salgado", 80054, LocalDate.of(1987, 04, 16), null, WorkStatus.AVAILABLE,
                null, "Adeco", "29-02-2023 13:01", LocalDate.of(2023, 10, 30), false,
                UserRole.EMPLOYEE, "rui.salgado@gmail.com", "915493251", "rui123", null, null);

        userRepository.save(testUser1);
        userRepository.save(testUser2);
        userRepository.save(nonExistentTestUserOnTheList);

        List<User> usersList = Arrays.asList(testUser1, testUser2);
        usersDtoList.addAll(userMapper.convertListUserToDto(usersList));
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDto> users = userService.getAllUsers();

        org.assertj.core.api.Assertions.assertThat(users).isNotEmpty();
        org.assertj.core.api.Assertions.assertThat(users).hasSize(3);
        org.assertj.core.api.Assertions.assertThat(users.get(0).firstName()).isEqualTo("Tiago");
        org.assertj.core.api.Assertions.assertThat(users.get(1).firstName()).isEqualTo("Elaine");
        org.assertj.core.api.Assertions.assertThat(users.get(2).firstName()).isEqualTo("Rui");
    }

    @Test
    void testCreateUserWithNoAssign() throws Exception{
        // Create User
        UserDto userDto = new UserDto(4L, "Fernando", "Castro", 80032, LocalDate.of(1996, 05, 02), null,
                WorkStatus.AVAILABLE, null, "INTERN" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2022, 01, 23),
                true, UserRole.ADMIN, "fernado.silva@gmail.com", "965217898", "fernando123", false, null, null);

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

        UserDto userDto = new UserDto(5L, "sadas", "asdasdas", 80030, underageBirthday, null,
                WorkStatus.AVAILABLE, null, "INTERN" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2022, 01, 23),
                true, UserRole.ADMIN, "fernado.silva@gmail.com", "965217898", "fernando123", false, null, null);


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
        UserDto userDto = new UserDto(5L, "sadas", "asdasdas", 80030, LocalDate.of(1997, 2, 12), null,
                WorkStatus.AVAILABLE, null, "INTERN" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2024, 1, 2),
                true, UserRole.ADMIN, "programtiago@gmail.com", "965217898", "teste123xx", false, null, null);


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
        UserDto userDto = new UserDto(5L, "xxxxx", "yyyyy", 80054, LocalDate.of(2000, 9, 30), null,
                WorkStatus.AVAILABLE, null, "INTERN" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2024, 1, 2),
                true, UserRole.ADMIN, "yyyyyyyyyyyyyyy123@gmail.com", "912317421", "teste123xx", false, null, null);

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
    @Test
    void shouldThrowExceptionWhenWorkNumberIsLessThanThirtyThousand() {
        UserDto userDto = new UserDto(5L, "Catarina", "Almeida", 29999, LocalDate.of(1989, 10, 10), null,
                WorkStatus.AVAILABLE, null, "INTERN" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2023, 5, 27),
                true, UserRole.ADMIN, "yyyyyyyysadjpoasjdasyyyyyyy123@gmail.com", "912365419", "safasdoajsdia", false, null, null);

        ConstraintViolationException throwable = Assertions.catchThrowableOfType(() -> userService.create(userDto), ConstraintViolationException.class);

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class);

    }

    /******************* workNumber > 100000 **************************/
    @Test
    void shouldThrowExceptionWhenWorkNumberIsGreaterThanHundredThousand() {
        UserDto userDto = new UserDto(5L, "Catarina", "Almeida", 100001, LocalDate.of(1989, 10, 10), null,
                WorkStatus.AVAILABLE, null, "INTERN" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2023, 5, 27),
                true, UserRole.ADMIN, "yyyyyyyysadjpoasjdasyyyyyyy123@gmail.com", "912365419", "safasdoajsdia", false, null, null);

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
