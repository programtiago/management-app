package com.netceed.management.management_app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.netceed.management.management_app.entity.user.*;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private User testUser1;
    private User testUser2;
    private UserDto testUserDto;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();

        testUser1 = new User(1L, "Tiago", "Silva", 30032, LocalDate.of(1996, 05, 02), null, WorkStatus.AVAILABLE,
                null, "Adeco", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2024, 05, 12), true,
                UserRole.ADMIN, "programtiago@gmail.com", "912341245", "tiago123", null, null);

        testUser2 = new User(2L, "Elaine", "Cruz", 80052, LocalDate.of(1998, 06, 16), null, WorkStatus.AVAILABLE,
                null, "Adeco", "30-10-2023 12:45", LocalDate.of(2023, 10, 30), true,
                UserRole.EMPLOYEE, "elaine.cruz@gmail.com", "965214523", "elaine123", null, null);

        userRepository.save(testUser1);
        userRepository.save(testUser2);



        /*
        UserDto userDto1 = new UserDto(1L, "Tiago", "Silva", 30032, LocalDate.of(1996, 05, 02), null,
                WorkStatus.AVAILABLE, null, "Adeco" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2024, 05, 12),
                true, UserRole.EMPLOYEE, "programtiago@gmail.com", "912342123", "tiago123", null, null);

         */

        /*
        UserDto userDto2 = new UserDto(2L, "Elaine", "Cruz", 80040, LocalDate.of(1998, 06, 16), null,
                WorkStatus.AVAILABLE, null, "Synergie" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2023, 11, 7),
                true, UserRole.EMPLOYEE, "elaine.cruz@gmail.com", "916521425", "elaine123", null, null);

         */
    }

    @Test
    @DisplayName("Happy Path Test: save user and return user dto")
    void givenCorrectUserDto_whenSaveUser_withNoAssignment_thenReturnUserDTO() throws Exception{}


    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDto> users = userService.getAllUsers();

        Assertions.assertNotNull(users);
        Assertions.assertEquals(2, users.size());

        Assertions.assertEquals("Tiago", users.get(0).firstName());
        Assertions.assertEquals("Elaine", users.get(1).firstName());

    }

    @Test
    public void testCreateUserWithNoAssign() throws Exception{
        // Create User
        UserDto userDto = new UserDto(3L, "Fernando", "Castro", 80032, LocalDate.of(1996, 05, 02), null,
                WorkStatus.AVAILABLE, null, "INTERN" , LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2022, 01, 23),
                true, UserRole.ADMIN, "fernado.silva@gmail.com", "965217898", "fernando123", false, null, null);

        UserDto createdUserDto = userService.create(userDto);

        Assertions.assertNotNull(createdUserDto.id());
        Assertions.assertEquals(userDto.firstName(), createdUserDto.firstName());
        Assertions.assertEquals(userDto.email(), createdUserDto.email());

        //Verify if the user is saved on database
        User savedUser = userRepository.findById(createdUserDto.id()).orElse(null);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(userDto.firstName(), savedUser.getFirstName());
        Assertions.assertEquals(userDto.email(), savedUser.getEmail());

    }

    @Test
    public void testExistsByWorkNumber_WhenExists_ReturnsTrue() {
        int existingWorkNumber = 30032;
        boolean exists = userService.workNumberExists(existingWorkNumber);
        Assertions.assertTrue(exists);
    }

    @Test
    public void testExistsByWorkNumber_WhenNotExists_ReturnsFalse() {
        int nonExistingWorkNumber = 84125;
        boolean exists = userService.workNumberExists(nonExistingWorkNumber);
        Assertions.assertFalse(exists);
    }

    @Test
    public void testExistsByEmail_WhenNotExists_ReturnsTrue(){
        String emailExists = "programtiago@gmail.com";

        boolean exists = userService.emailAlreadyExists(emailExists);
        Assertions.assertTrue(exists);
    }

    @Test
    public void testExistsByEmail_WhenNotExists_ReturnsFalse(){
        String emailExists = "afonso.martins@gmail.com";

        boolean nonExistingEmail = userService.emailAlreadyExists(emailExists);
        Assertions.assertFalse(nonExistingEmail);
    }

    @Test
    public void testGetUserById() {
        UserDto foundUser = userService.getById(testUser1.getId());
        Assertions.assertEquals(testUser1.getFirstName(), foundUser.firstName());
        Assertions.assertEquals(testUser1.getLastName(), foundUser.lastName());
        Assertions.assertEquals(testUser1.getEmail(), foundUser.email());
    }

    @Test
    public void testUpdateUser(){
        UserMapper userMapper = new UserMapper();
        testUser2.setActive(false);
        testUser2.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        testUser2.setWorkStatus(WorkStatus.VACATION);
        testUser2.setAvailableForVacation(true);

        UserDto userToUpdateDto = userMapper.toDto(testUser2);
        User userToUpdate = userService.update(userToUpdateDto, userToUpdateDto.id());

        Assertions.assertEquals(false, userToUpdate.isActive());
        Assertions.assertEquals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), userToUpdate.getUpdatedAt());
        Assertions.assertEquals(WorkStatus.VACATION, userToUpdate.getWorkStatus());
        Assertions.assertEquals(true, userToUpdate.isAvailableForVacation());

    }

    @Test
    public void testDeleteUser(){
        userService.delete(testUser1.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getById(testUser1.getId()));
    }




}
