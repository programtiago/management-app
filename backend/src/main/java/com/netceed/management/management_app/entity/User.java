package com.netceed.management.management_app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netceed.management.management_app.enums.UserRole;
import com.netceed.management.management_app.enums.WorkStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

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
    @NotBlank(message = "The First Name is mandatory")
    private String firstName;
    @NotBlank(message = "The Last Name is mandatory")
    private String lastName;
    @Range(min = 30000, max = 100000, message = "The Work Number must be between 3000 and 100000")
    private int workNumber;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate birthdayDate;
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus = WorkStatus.AVAILABLE;
    @ManyToOne
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;
    private String recruitmentCompany;
    private String registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate admissionDate;
    private boolean isActive = true;
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.EMPLOYEE;
    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "The Contact Number is mandatory")
    @Size(min = 9, max = 9)
    private String contactNumber;
    @NotBlank(message = "The Password is mandatory")
    private String password;
    private boolean isAvailableForVacation; //the user (employee) has to be 6 months plus admission date to  be able to take vacations
    private String updatedAt;

        public User(String firstName, String lastName, String email, int workNumber, LocalDate birthdayDate, String password, LocalDate admissionDate, Department department, UserRole userRole, Shift shift, String recruitmentCompany, String contactNumber){
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
        this.shift = shift;
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

    public User(Long id, String firstName, String lastName, int workNumber, LocalDate birthdayDate, Department department, WorkStatus workStatus, Shift shift, String recruitmentCompany, String registryDate, LocalDate admissionDate, boolean active, UserRole userRole, String email, String contactNumber, String password, String updatedAt) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.workNumber = workNumber;
            this.birthdayDate = birthdayDate;
            this.department = department;
            this.workStatus = workStatus;
            this.shift = shift;
            this.recruitmentCompany = recruitmentCompany;
            this.registryDate = registryDate;
            this.admissionDate = admissionDate;
            this.isActive = active;
            this.userRole = userRole;
            this.email = email;
            this.contactNumber = contactNumber;
            this.password = password;
            this.updatedAt = updatedAt;
    }
}
