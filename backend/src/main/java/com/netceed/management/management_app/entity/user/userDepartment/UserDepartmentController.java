package com.netceed.management.management_app.entity.user.userDepartment;

import com.netceed.management.management_app.entity.department.DepartmentDto;
import com.netceed.management.management_app.entity.user.UserDto;
import com.netceed.management.management_app.service.UserDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user-departments")
@RequiredArgsConstructor
public class UserDepartmentController {

    private final UserDepartmentService userDepartmentService;

    @GetMapping
    public List<UserDepartmentDto> getAll(){
        return userDepartmentService.getAllAssignments();
    }
    //This endpoint will allow to send array of longs. Can have 1 element or more
    @PostMapping("/{departmentId}/users")
    public List<UserDepartmentDto> assignUserToDepartments(@PathVariable Long departmentId, @RequestBody List<Long> usersId){
        return userDepartmentService.assignUserToDepartments(departmentId, usersId);
    }

    @GetMapping("/{departmentId}/users")
    public List<UserDto> getAllUsersFromDepartmentId(@PathVariable Long departmentId){
        return userDepartmentService.getAllEmployeesFromDepartmentId(departmentId);
    }

    @DeleteMapping("/{departmentId}/users/{userId}")
    public void removeUserFromDepartment(@PathVariable Long departmentId, @PathVariable Long userId){
        userDepartmentService.removeUserFromDepartment(departmentId, userId);
    }
}
