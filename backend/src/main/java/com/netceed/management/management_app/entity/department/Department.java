package com.netceed.management.management_app.entity.department;

import com.netceed.management.management_app.entity.user.userDepartment.UserDepartment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "TB_DEPARTMENT")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "value is required")
    private String codeValue;
    @NotBlank(message = "description is required")
    private String description;
    private String registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    private int totalEmployees;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<UserDepartment> userDepartments = new HashSet<>();

    public Department(Long id, String codeValue, String description, int totalEmployees){
        this.id = id;
        this.codeValue = codeValue;
        this.description = description;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.totalEmployees = totalEmployees;
    }

    public Department(String codeValue, String description, int totalEmployees){
        this.codeValue = codeValue;
        this.description = description;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.totalEmployees = totalEmployees;
    }
}
