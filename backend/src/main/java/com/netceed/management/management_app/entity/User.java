package com.netceed.management.management_app.entity;

import com.netceed.management.management_app.enums.ShiftType;
import com.netceed.management.management_app.enums.UserRole;
import com.netceed.management.management_app.enums.WorkStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private int workNumber;
    private LocalDate birthdayDate;
    private String department;
    private WorkStatus workStatus = WorkStatus.AVAILABLE;
    private ShiftType shiftType;
    private String recruitmentCompany;
    private String registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    private LocalDate admissionDate;
    private boolean isActive = true;
    private UserRole userRole = UserRole.EMPLOYEE;
    private String email;
    private String contactNumber;
    private String password;
    private String updatedAt;

        public User(String firstName, String lastName, String email, int workNumber, LocalDate birthdayDate, String password, LocalDate admissionDate, String department, UserRole userRole, ShiftType shiftType, String recruitmentCompany, String contactNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.workNumber = workNumber;
        this.birthdayDate = birthdayDate;
        this.department = department;
        this.email = email;
        this.password = password;
        this.admissionDate = admissionDate;
        this.contactNumber = contactNumber;
        this.userRole = userRole;
        this.shiftType = shiftType;
        this.recruitmentCompany = recruitmentCompany;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public User(String firstName, String lastName, LocalDate admissionDate, String email, String password, UserRole userRole){
        this.firstName = firstName;
        this.lastName = lastName;
        this.admissionDate = admissionDate;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
