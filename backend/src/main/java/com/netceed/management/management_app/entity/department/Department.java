package com.netceed.management.management_app.entity.department;

import com.netceed.management.management_app.entity.building.buildingAllocation.BuildingAllocation;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
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
    @Column(name = "code_value", nullable = false, length = 5)
    private String codeValue;
    @NotBlank(message = "description is required")
    @Column(name = "description", nullable = false, length = 50)
    private String description;
    @Column(name = "registry_date", nullable = false, length = 19)
    private String registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    @Column(name = "total_employees", nullable = false)
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
