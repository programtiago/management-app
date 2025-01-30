package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.department.DepartmentDto;
import com.netceed.management.management_app.entity.department.DepartmentMapper;
import com.netceed.management.management_app.entity.user.UserDto;
import com.netceed.management.management_app.entity.user.UserMapper;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartment;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartmentDto;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartmentMapper;
import com.netceed.management.management_app.entity.user.User;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.repository.UserDepartmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDepartmentService {

    private final UserDepartmentRepository userDepartmentRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    private final UserDepartmentMapper userDepartmentMapper;
    private final DepartmentMapper departmentMapper;
    private final UserMapper userMapper;

    //If user exists and department we can assign user to department
    /*
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
     */

    public List<UserDepartmentDto> getAllAssignments(){
        return userDepartmentRepository.findAll().stream().map(userDepartmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDepartmentDto> assignUserToDepartments(Long departmentId, List<Long> usersId) {
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
                    userDepartment.setAssignedDate(LocalDateTime.now());
                    userDepartment.setComments("User " + user.getFirstName() + " " + user.getLastName() + " with work number " + user.getWorkNumber() + " was assigned to department " + department.getDescription() + " at " +
                            userDepartment.getAssignedDate());
                    userDepartments.add(userDepartment);

                }
            }
            department.setTotalEmployees(usersId.size() + department.getTotalEmployees());

            departmentRepository.save(department);
        }

        List<UserDepartment> assignmentsDepartmentToUsers = userDepartmentRepository.saveAll(userDepartments);

        return userDepartmentMapper.convertListDepartmentToListDepartmentDto(assignmentsDepartmentToUsers);
    }

    public List<UserDto> getAllEmployeesFromDepartmentId(Long departmentId){
        List<User> usersByDepartment = userRepository.findAllUsersByDepartmentId(departmentId);

        return userMapper.convertListUserToDto(usersByDepartment);
    }

    public void removeUserFromDepartment(Long departmentId, Long userId){
        Optional<DepartmentDto> departmentDto = departmentRepository.findById(departmentId).map(departmentMapper::toDto);
        Optional<UserDto> userDto = userRepository.findById(userId).map(userMapper::toDto);
        //UserDepartment assignmentToRemove = null;
        //UserDepartmentDto userDepartmentAssignmentDto;
        UserDepartment userDepartmentAssignment = new UserDepartment();

        if (departmentDto.isPresent()){
            if (userDto.isPresent()){
                //Check if assignment exists with departmentId and userId given
                userDepartmentAssignment = userDepartmentRepository.findUserDepartmentByUserAndDepartment(departmentId, userId);

                if (userDepartmentAssignment == null)
                    throw new NullPointerException("No assignment found for this department and user ! ");

                /*
                if (userDepartmentAssignmentDto.id() != null){
                    assignmentToRemove = userDepartmentMapper.toEntity(userDepartmentAssignmentDto);
                }else{
                    throw new NullPointerException("No assignment found ! ");
                }
                 */

                //if assingment exists
                User userToRemove = userMapper.toEntity(userDto.get());
                Department departmentUser = departmentMapper.toEntity(departmentDto.get());

                //userToRemove.setDepartment(null);
                //departmentUser.setUsers(null);

                //remove assingment from table user_department
                userDepartmentRepository.deleteById(userDepartmentAssignment.getId());

                //decrease totalEmployees after deleting the assignment
                departmentUser.setTotalEmployees(departmentUser.getTotalEmployees() - 1);
            }else{
                throw new ResourceNotFoundException("User with id " + userDto.get().id() + " not found");
            }
        }
    }


}
