package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id){
        return userRepository.findById(id);
    }
}
