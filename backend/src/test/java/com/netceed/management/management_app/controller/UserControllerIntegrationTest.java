package com.netceed.management.management_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netceed.management.management_app.ManagementAppApplication;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.mapper.UserMapper;
import com.netceed.management.management_app.enums.UserRole;
import com.netceed.management.management_app.enums.WorkStatus;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllUsersAPI() throws Exception {
        // Create two users
        UserDto userDto1 = new UserDto(1L, "Tiago", "Silva", 30032, LocalDate.of(2012, 02, 23),
                null, WorkStatus.AVAILABLE, null, "Adeco",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.now(), true, UserRole.EMPLOYEE, "programtiago@gmail.com",
                "912343212", "tiago123", null, null);

        UserDto userDto2 = new UserDto(2L, "Elaine", "Cruz", 30083, LocalDate.of(1998, 06, 16),
                null, WorkStatus.AVAILABLE, null, "Synergie",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), LocalDate.now(), true, UserRole.EMPLOYEE, "elaine.cruz@gmail.com",
                "912343422", "elaine123", null, null);

        List<UserDto> usersList = Arrays.asList(userDto1, userDto2);

        Mockito.when(userService.getAllUsers()).thenReturn(usersList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(2)));
                //.andExpect(jsonPath("$[0].firstName", is("Tiago")))
                //.andExpect(jsonPath("$[1].firstName", is("Elaine")));

        verify(userService, times(1)).getAllUsers();
        //verifyNoMoreInteractions(userService);

    }
}