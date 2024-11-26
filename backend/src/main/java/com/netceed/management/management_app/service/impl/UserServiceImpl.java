package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.UserDto;
import com.netceed.management.management_app.entity.mapper.UserMapper;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::toDto)
                .collect(Collectors.toList());
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
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()){
            throw new ResourceNotFoundException("Operation failed because the resource with the id " + id + " doesn't exist.");
        }

        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setUserRole(newUser.getUserRole());
                    user.setActive(newUser.isActive());
                    user.setWorkNumber(newUser.getWorkNumber());
                    user.setContactNumber(newUser.getContactNumber());
                    user.setEmail(newUser.getEmail());
                    user.setDepartment(newUser.getDepartment());
                    user.setPassword(newUser.getPassword());
                    user.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

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

    @Override
    public boolean birthdayDateIsValid(LocalDate birthdayDate) {
        int yearOfToday = LocalDate.now().getYear();
        int yearOfBirthday = birthdayDate.getYear();

        return (yearOfToday - yearOfBirthday) >= 18;
    }

    @Override
    public User deactivateAccount(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        user.setActive(false);

        return userRepository.save(user);
    }

    @Override
    public User activateAccount(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        user.setActive(true);

        return userRepository.save(user);

    }

}
