package com.netceed.management.management_app.entity.user.userDepartment;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_USER_DEPARTMENT")
public class UserDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private LocalDateTime assignmentDateTime;
    private String comments;
}
