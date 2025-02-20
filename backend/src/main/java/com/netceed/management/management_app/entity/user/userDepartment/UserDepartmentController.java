package com.netceed.management.management_app.entity.user.userDepartment;

import com.netceed.management.management_app.entity.trackaudit.TrackAuditService;
import com.netceed.management.management_app.service.UserDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user-departments")
@RequiredArgsConstructor
public class UserDepartmentController {

    private final UserDepartmentService userDepartmentService;
    private final TrackAuditService trackAuditService;

    @GetMapping
    public List<UserDepartmentDto> getAll(){
        return userDepartmentService.getAll();
    }

    //This endpoint will allow to send array of longs. Can have 1 element or more
    @PostMapping("/{departmentId}/users")
    public List<UserDepartmentDto> assignUsersToDepartment(@PathVariable Long departmentId, @RequestBody List<Long> usersId){
        List<UserDepartmentDto> userDepartmentDtoList = userDepartmentService.assignUserToDepartments(departmentId, usersId);

        for (UserDepartmentDto userDepartmentDto : userDepartmentDtoList){
            if (userDepartmentDto != null){
                trackAuditService.logAction(Collections.singletonList(userDepartmentDto.id()), "Assignment of user [ " + userDepartmentDto.user().getWorkNumber() + " ] - " + userDepartmentDto.user().getFirstName() + " " +
                        userDepartmentDto.user().getLastName(), "testUsername", "UserDepartment");
            }
        }

        return userDepartmentDtoList;
    }

    @DeleteMapping("/{departmentId}/users/{userId}")
    public void removeUserFromDepartment(@PathVariable Long departmentId, @PathVariable Long userId){
        userDepartmentService.removeUserFromDepartment(departmentId, userId);
        trackAuditService.logAction(Collections.singletonList(userId), "Removed user from department", "testUsername", "UserDepartment");
    }

    @GetMapping("/department/{departmentId}/user/{userId}")
    public UserDepartmentDto getAssignmentUserToDepartment(@PathVariable Long departmentId, @PathVariable Long userId){
        return userDepartmentService.getAssignmentByUserIdAndDepartmentId(departmentId, userId);
    }
}
