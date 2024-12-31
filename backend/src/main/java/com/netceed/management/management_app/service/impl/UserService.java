package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.mapper.UserMapper;
import com.netceed.management.management_app.exception.BirthayDateException;
import com.netceed.management.management_app.exception.EmailAlreadyExistsException;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getById(Long id){
        return userRepository.findById(id).map(userMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("User resource not found with id " + id));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User update(UserDto userUpdate, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userUpdate.firstName());
                    user.setLastName(userUpdate.lastName());
                    user.setUserRole(userUpdate.userRole());
                    user.setActive(userUpdate.isActive());
                    user.setWorkNumber(userUpdate.workNumber());
                    user.setContactNumber(userUpdate.contactNumber());
                    user.setEmail(userUpdate.email());
                    user.setDepartment(userUpdate.department());
                    user.setPassword(userUpdate.password());
                    user.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Operation failed because the resource with the id " + id + " doesn't exist."));
    }

    public User create(UserDto newUser) throws NoSuchFieldException {
        boolean workNumberAlreadyExists = workNumberExists(newUser.workNumber());
        boolean emailAlreadyExists = emailAlreadyExists(newUser.email());
        boolean birthdayDateGivenIsValid = birthdayDateIsValid(newUser.birthdayDate());

        if (workNumberAlreadyExists) {
            throw new NoSuchFieldException("Work Number " +  newUser.workNumber() + " already registered");
        }
        if (emailAlreadyExists){
            throw new EmailAlreadyExistsException("Email " +  newUser.email() + " already registered");
        }

        if (!birthdayDateGivenIsValid){
            throw new BirthayDateException("Birthday Date given indicate the user doesn't have 18 years or more");
        }

        newUser.department().setTotalEmployees(departmentRepository.getTotalOfEmployeesByDepartment(newUser.id()) + 1);

        return userRepository.save(userMapper.toEntity(newUser));
    }

    public boolean workNumberExists(int workNumber) {
        return userRepository.findByWorkNumber(workNumber).isPresent();
    }

    public boolean emailAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean birthdayDateIsValid(LocalDate birthdayDate) {
        int yearOfToday = LocalDate.now().getYear();
        int yearOfBirthday = birthdayDate.getYear();

        return (yearOfToday - yearOfBirthday) >= 18;
    }

    public UserDto deactivateAccount(Long id) throws Exception {
        UserDto userToDeactivateFound = getById(id);
        User userEntity = userMapper.toEntity(userToDeactivateFound);

        if (userToDeactivateFound.isActive()) {
            try {
                userToDeactivateFound.setIsActive(false);
            } catch (Exception e) {
                throw new Exception("Something went wrong. Please try again");
            }
        } else {
            throw new IllegalArgumentException("User already deactivated. Impossible to active with id " + id);
        }

        userRepository.save(userEntity);

        return userToDeactivateFound;
    }

    public UserDto activateAccount(Long id) throws Exception {
        UserDto userToActivateFound = getById(id);
        User userEntity = userMapper.toEntity(userToActivateFound);

        if (!userToActivateFound.isActive()) {
            try {
                userToActivateFound.setIsActive(true);
            } catch (Exception e) {
                throw new Exception("Something went wrong. Please try again");
            }
        } else {
            throw new IllegalArgumentException("User already activated. Impossible to active with id " + id);
        }

        userRepository.save(userEntity);

        return userToActivateFound;
    }

    public List<UserDto> getUsersByDepartment(Long id) {
        return userRepository.findAll().stream()
                .filter(user -> user.getDepartment().getId().equals(id)).map(userMapper::toDto)
                .toList();
    }

}
