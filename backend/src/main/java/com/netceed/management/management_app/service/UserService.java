package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
    UserDto getById(Long id);
    void delete(Long id);
    User update(UserDto user, Long id);
    UserDto create(User user);
    boolean workNumberExists(int workNumber);
    boolean emailAlreadyExists(String email);
    boolean birthdayDateIsValid(LocalDate birthdayDate);
    User deactivateAccount(Long id);
    User activateAccount(Long id);
    List<UserDto> getUsersByDepartment(Long id);
}

