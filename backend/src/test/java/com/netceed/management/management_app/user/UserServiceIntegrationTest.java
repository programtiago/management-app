package com.netceed.management.management_app.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.netceed.management.management_app.entity.user.*;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();

        User user1 = new User(1L, "Tiago", "Silva", 30032, LocalDate.of(1996, 05, 02), null, WorkStatus.AVAILABLE,
                null, "Adeco", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.of(2024, 05, 12), true,
                UserRole.ADMIN, "programtiago@gmail.com", "912341245", "tiago123", null, null);

        User user2 = new User(2L, "Elaine", "Cruz", 80052, LocalDate.of(1998, 06, 16), null, WorkStatus.AVAILABLE,
                null, "Adeco", "30-10-2023 12:45", LocalDate.of(2023, 10, 30), true,
                UserRole.EMPLOYEE, "elaine.cruz@gmail.com", "965214523", "elaine123", null, null);

        userRepository.save(user1);
        userRepository.save(user2);

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
}
