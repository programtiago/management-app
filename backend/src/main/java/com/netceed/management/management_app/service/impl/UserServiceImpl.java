package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
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

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(User newUser, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setUserRole(newUser.getUserRole());
                    user.setActive(newUser.isActive());
                    user.setContactNumber(newUser.getContactNumber());
                    user.setEmail(newUser.getEmail());
                    user.setDepartment(newUser.getDepartment());
                    user.setPassword(newUser.getPassword());

                    return userRepository.save(user);
                }).orElseThrow();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean workNumberExists(int workNumber) {
        return userRepository.findByWorkNumber(workNumber).isPresent();
    }

    @Override
    public boolean emailAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
