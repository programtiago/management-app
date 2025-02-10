package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.department.DepartmentDto;
import com.netceed.management.management_app.entity.department.DepartmentMapper;
import com.netceed.management.management_app.entity.user.User;
import com.netceed.management.management_app.entity.user.UserDto;
import com.netceed.management.management_app.entity.user.UserMapper;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartment;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartmentDto;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartmentMapper;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDepartmentService {

    private final UserDepartmentRepository userDepartmentRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    private final UserDepartmentMapper userDepartmentMapper;
    private final DepartmentMapper departmentMapper;
    private final UserMapper userMapper;

    public List<UserDepartmentDto> getAll() {
        return userDepartmentRepository.findAll()
                .stream().map(userDepartmentMapper::toDto)
                .collect(Collectors.toList());
    }

    /***************************** Assign a existent user to a existent department *************************************/
    //Both of the objects have to exist before to create the assignment
    public List<UserDepartmentDto> assignUserToDepartments (Long departmentId, List<Long> usersId){
            Optional<Department> departmentOpt = departmentRepository.findById(departmentId);
            List<UserDepartment> userDepartments = new ArrayList<>();

            if (departmentOpt.isPresent()) {
                Department department = departmentOpt.get();
                for (Long userId : usersId) {
                    Optional<User> userOpt = userRepository.findById(userId);
                    if (userOpt.isPresent()) {
                        User user = userOpt.get();

                        UserDepartment userDepartment = new UserDepartment();

                        userDepartment.setDepartment(department);
                        userDepartment.setUser(user);
                        userDepartment.setAssignmentDateTime(LocalDateTime.now());
                        userDepartment.setComments("User " + user.getFirstName() + " " + user.getLastName() + " with work number " + user.getWorkNumber() + " was assigned to department " + department.getDescription() + " at " +
                                userDepartment.getAssignmentDateTime());
                        userDepartments.add(userDepartment);

                    }
                }
                department.setTotalEmployees(usersId.size() + department.getTotalEmployees());

                departmentRepository.save(department);
            }

            List<UserDepartment> assignmentsDepartmentToUsers = userDepartmentRepository.saveAll(userDepartments);

            return userDepartmentMapper.convertListDepartmentToListDepartmentDto(assignmentsDepartmentToUsers);
        }

        public void removeUserFromDepartment (Long departmentId, Long userId){
            Optional<DepartmentDto> departmentDto = departmentRepository.findById(departmentId).map(departmentMapper::toDto);
            Optional<UserDto> userDto = userRepository.findById(userId).map(userMapper::toDto);
            //UserDepartment assignmentToRemove = null;
            //UserDepartmentDto userDepartmentAssignmentDto;
            UserDepartment userDepartmentAssignment = new UserDepartment();

            if (departmentDto.isPresent()) {
                if (userDto.isPresent()) {
                    //Check if assignment exists with departmentId and userId given
                    userDepartmentAssignment = userDepartmentRepository.findUserDepartmentByUserAndDepartment(departmentId, userId);

                    if (userDepartmentAssignment == null)
                        throw new NullPointerException("No assignment found for this department and user ! ");

                    //if assingment exists
                    User userToRemove = userMapper.toEntity(userDto.get());
                    Department departmentUser = departmentMapper.toEntity(departmentDto.get());

                    //remove assingment from table user_department
                    userDepartmentRepository.deleteById(userDepartmentAssignment.getId());

                    //decrease totalEmployees after deleting the assignment
                    departmentUser.setTotalEmployees(departmentUser.getTotalEmployees() - 1);
                } else {
                    throw new ResourceNotFoundException("User with id " + userDto.get().id() + " not found");
                }
        }
    }

    /***************************** Assign a existent department to a existent user *************************************/
    //Both of the objects have to exist before to create the assignment
    public UserDepartmentDto assignDepartmentToUser(Long userId, Long departmentId){
        User user = userRepository.findById(userId).orElseThrow();
        Department department = departmentRepository.findById(departmentId).orElseThrow();

        UserDepartment assigment = new UserDepartment();

        assigment.setAssignmentDateTime(LocalDateTime.now());
        assigment.setComments("User: " + user.getFirstName() + " " + user.getLastName() + " with work number " + user.getWorkNumber() + " was assigned to department " + department.getCodeValue() + " at " + assigment.getAssignmentDateTime() );
        assigment.setDepartment(department);
        assigment.setUser(user);

        department.setTotalEmployees(department.getTotalEmployees() + 1);

        departmentRepository.save(department);

        userDepartmentRepository.save(assigment);

        return userDepartmentMapper.toDto(assigment);
    }
}
