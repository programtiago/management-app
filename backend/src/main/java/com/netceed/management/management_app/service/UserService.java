package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();
    Optional<User> getById(Long id);
    void delete(Long id);
    User update(User user, Long id);
    User create(User user);
    boolean workNumberExists(int workNumber);
    boolean emailAlreadyExists(String email);
    boolean birthdayDateIsValid(LocalDate birthdayDate);
}

