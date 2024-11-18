package com.netceed.management.management_app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netceed.management.management_app.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "The First Name is mandatory")
    private String firstName;
    @NotBlank(message = "The Last Name is mandatory")
    private String lastName;
    @Range(min = 30000, max = 100000, message = "The Work Number must be between 3000 and 100000")
    private int workNumber;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdayDate;
    private String department;
    private String registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    private String admissionDate;
    private boolean isActive = true;
    private UserRole userRole = UserRole.EMPLOYEE;
    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "The Contact Number is mandatory")
    @Size(max = 10)
    private String contactNumber;
    @NotBlank(message = "The Password is mandatory")
    private String password;
    private String updatedAt;

    public User(String firstName, String lastName, String email, int workNumber, LocalDate birthdayDate, String password, String department, UserRole userRole, String contactNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.admissionDate = admissionDate;
        this.workNumber = workNumber;
        this.birthdayDate = birthdayDate;
        this.department = department;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.userRole = userRole;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public User(String firstName, String lastName, String email, String password, UserRole userRole){
        this.firstName = firstName;
        this.lastName = lastName;
        this.admissionDate = admissionDate;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
