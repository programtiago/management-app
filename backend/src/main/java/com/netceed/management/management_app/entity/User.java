package com.netceed.management.management_app.entity;

import com.netceed.management.management_app.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String department;
    private String registryDate;
    private boolean isActive;
    private UserRole userRole;
    private String email;
    private String contactNumber;
    private String password;

    public User(String firstName, String lastName, int workNumber, String department, String contactNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.workNumber = workNumber;
        this.department = department;
        this.contactNumber = contactNumber;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public User(String firstName, String lastName, String email, String password, UserRole userRole){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
