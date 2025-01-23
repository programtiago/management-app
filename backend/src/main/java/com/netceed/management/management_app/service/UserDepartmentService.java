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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDepartmentService {

    private final UserDepartmentRepository userDepartmentRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    private final UserDepartmentMapper userDepartmentMapper;

    //If user exists and department we can assign user to department
    public UserDepartmentDto assignUserToDepartment(Long userId, Long departmentId){
        Optional<User> user = userRepository.findById(userId);
        Optional<Department> department = departmentRepository.findById(departmentId);

        UserDepartment userDepartment = new UserDepartment();

        Department departmentFound = department.get();
        User userFound = user.get();

        userDepartment.setDepartment(departmentFound);
        userDepartment.setUser(userFound);
        userDepartment.setAssignedDate(LocalDateTime.now());
        userDepartment.setComments("User " + user.get().getFirstName() + " " + user.get().getLastName() + " with work number " + user.get().getWorkNumber() + " was assigned to department " + department.get().getDescription() + " at " +
                userDepartment.getAssignedDate());

        userDepartmentRepository.save(userDepartment);

        department.get().setTotalEmployees(department.get().getTotalEmployees() + 1);
        departmentRepository.save(departmentFound);

        return userDepartmentMapper.toDto(userDepartment);
    }

}
