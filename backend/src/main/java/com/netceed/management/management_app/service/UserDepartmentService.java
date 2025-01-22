package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.department.userDepartment.UserDepartment;
import com.netceed.management.management_app.entity.department.userDepartment.UserDepartmentDto;
import com.netceed.management.management_app.entity.department.userDepartment.UserDepartmentMapper;
import com.netceed.management.management_app.entity.user.User;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.repository.UserDepartmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserDepartmentService {

    private final UserDepartmentRepository userDepartmentRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    private final UserDepartmentMapper userDepartmentMapper;

    public UserDepartmentDto assignUserToDepartment(Long userId, Long departmentId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        UserDepartment userDepartment = new UserDepartment();

        userDepartment.setDepartment(department);
        userDepartment.setUser(user);
        userDepartment.setAssignedDate(LocalDateTime.now());
        userDepartment.setComments("User " + user.getFirstName() + " " + user.getLastName() + " with work number " + user.getWorkNumber() + " was assigned to department " + department.getDescription() + " at " +
                userDepartment.getAssignedDate());

        userDepartmentRepository.save(userDepartment);

        department.setTotalEmployees(department.getTotalEmployees() + 1);
        departmentRepository.save(department);

        return userDepartmentMapper.toDto(userDepartment);
    }

}
