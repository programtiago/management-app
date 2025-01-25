package com.netceed.management.management_app.entity.user.userDepartment;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.user.User;

import java.time.LocalDateTime;

public record UserDepartmentDto(Long id, User user, Department department, LocalDateTime assignedDate,String comments) {
    public UserDepartmentDto(Long id, User user, Department department, LocalDateTime assignedDate, String comments) {
        this.id = id;
        this.user = user;
        this.department = department;
        this.assignedDate = assignedDate;
        this.comments = comments;
    }
}

