package com.netceed.management.management_app.entity.user.userDepartment;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.user.User;

import java.time.LocalDateTime;

public record UserDepartmentDto (Long id, User user, Department department, LocalDateTime assignmentDateTime, String comments){
}
