package com.netceed.management.management_app.entity.user.userDepartment;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @Column(nullable = false)
    private LocalDateTime assignedDate;
    private String comments;
}
