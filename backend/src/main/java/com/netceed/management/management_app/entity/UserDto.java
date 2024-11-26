package com.netceed.management.management_app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netceed.management.management_app.enums.ShiftType;
import com.netceed.management.management_app.enums.UserRole;
import com.netceed.management.management_app.enums.WorkStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;


public record UserDto (
    Long id,
    @NotBlank(message = "The First Name is mandatory")
    String firstName,
    @NotBlank(message = "The Last Name is mandatory")
    String lastName,
    @Range(min = 30000, max = 100000, message = "The Work Number must be between 3000 and 100000")
    int workNumber,
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    LocalDate birthdayDate,
    String department,
    @Enumerated(EnumType.STRING)
    WorkStatus workStatus,
    @Enumerated(EnumType.STRING)
    ShiftType shiftType,
    String recruitmentCompany,
    String registryDate,
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    LocalDate admissionDate,
    boolean isActive,
    @Enumerated(EnumType.STRING)
    UserRole userRole,
    @Email
    @NotBlank(message = "Email is mandatory")
    String email,
    @NotBlank(message = "The Contact Number is mandatory")
    @Size(min = 9, max = 9)
    String contactNumber,
    @NotBlank(message = "The Password is mandatory")
    String password,
    String updatedAt
){
    public UserDto(Long id, String firstName, String lastName, int workNumber, LocalDate birthdayDate, String department, WorkStatus workStatus, ShiftType shiftType, String recruitmentCompany, String registryDate, LocalDate admissionDate, boolean isActive, UserRole userRole, String email, String contactNumber, String password, String updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workNumber = workNumber;
        this.birthdayDate = birthdayDate;
        this.department = department;
        this.workStatus = workStatus;
        this.shiftType = shiftType;
        this.recruitmentCompany = recruitmentCompany;
        this.registryDate = registryDate;
        this.admissionDate = admissionDate;
        this.isActive = isActive;
        this.userRole = userRole;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.updatedAt = updatedAt;
    }
}
