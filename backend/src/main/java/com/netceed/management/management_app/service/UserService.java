package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();
    Optional<User> getById(Long id);
}

