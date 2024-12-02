package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.entity.dto.EquipmentDto;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.mapper.EquipmentMapper;
import com.netceed.management.management_app.entity.mapper.UserMapper;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.repository.EquipmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserMapper userMapper;
    private final EquipmentMapper equipmentMapper;
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

    @Override
    public UserDto create(User user) {
        user.getDepartment().setTotalEmployees(departmentRepository.getTotalOfEmployeesByDepartment(user.getId()) + 1);
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

    @Override
    public List<UserDto> getUsersByDepartment(Long id) {
        return userRepository.findAll().stream()
                .filter(user -> user.getDepartment().getId().equals(id)).map(userMapper::toDto)
                .toList();
    }

}
