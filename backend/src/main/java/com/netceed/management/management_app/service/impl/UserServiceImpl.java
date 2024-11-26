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
    public UserDto getById(Long id){
        return userRepository.findById(id).map(userMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("User resource not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(UserDto newUser, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.firstName());
                    user.setLastName(newUser.lastName());
                    user.setUserRole(newUser.userRole());
                    user.setActive(newUser.isActive());
                    user.setWorkNumber(newUser.workNumber());
                    user.setContactNumber(newUser.contactNumber());
                    user.setEmail(newUser.email());
                    user.setDepartment(newUser.department());
                    user.setPassword(newUser.password());
                    user.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Operation failed because the resource with the id " + id + " doesn't exist."));
    }

    @Override
    public UserDto create(User user) {
        User userToInsert = new User();
        userToInsert.setId(user.getId());
        userToInsert.setFirstName(user.getFirstName());
        userToInsert.setLastName(user.getLastName());
        userToInsert.setBirthdayDate(user.getBirthdayDate());
        userToInsert.setActive(user.isActive());
        userToInsert.setUserRole(user.getUserRole());
        userToInsert.setPassword(user.getPassword());
        userToInsert.setEmail(user.getEmail());
        userToInsert.setDepartment(user.getDepartment());
        userToInsert.setContactNumber(user.getContactNumber());
        userToInsert.setUpdatedAt(user.getUpdatedAt());
        userToInsert.setShiftType(user.getShiftType());
        userToInsert.setWorkStatus(user.getWorkStatus());
        userToInsert.setAdmissionDate(user.getAdmissionDate());

        userRepository.save(user);

        return userMapper.toDto(user);
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
