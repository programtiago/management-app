package com.netceed.management.management_app.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartment;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "TB_USER")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @NotBlank(message = "The First Name is mandatory")
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    @NotBlank(message = "The Last Name is mandatory")
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    @Range(min = 30000, max = 100000, message = "The Work Number must be between 3000 and 100000")
    @Column(name = "work_number", nullable = false, length = 9)
    private int workNumber;
    @Column(name = "birthday_date", nullable = false)
    private LocalDate birthdayDate;
    @Column(name = "nif", nullable = false, length = 9)
    @Length(min = 9, max = 9)
    private String nif;
    @Enumerated(EnumType.STRING)
    @Column(name = "work_status", nullable = false)
    private WorkStatus workStatus = WorkStatus.AVAILABLE;
    @Column(name = "recruitment_company", nullable = false)
    private String recruitmentCompany;
    @Column(name = "registry_date", nullable = false, length = 19)
    private String registryDate;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;
    @Email
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email", nullable = false, length = 55)
    private String email;
    @NotBlank(message = "The Contact Number is mandatory")
    @Size(min = 9, max = 9)
    @Column(name = "contact_number", nullable = false)
    private String contactNumber;
    @NotBlank(message = "The Password is mandatory")
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "is_available_for_vacation", nullable = false)
    private boolean isAvailableForVacation; //the user (employee) has to be 6 months plus admission date to  be able to take vacations
    @Column(name = "updated_at", nullable = false)
    private String updatedAt;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<UserEquipment> userEquipments = new HashSet<>();
    @Column(name = "user_already_on_department", nullable = false)
    private boolean userAlreadyOnDepartment;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserDepartment> userDepartments = new HashSet<>();

    public User(Long id, String firstName, String lastName, String email, int workNumber, LocalDate birthdayDate, String password, LocalDate admissionDate, boolean isActive, UserRole userRole, String nif, String recruitmentCompany, String registryDate, String contactNumber, Set<UserEquipment> equipments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workNumber = workNumber;
        this.birthdayDate = birthdayDate;
        this.email = email;
        this.password = password;
        this.admissionDate = admissionDate;
        this.isActive = isActive;
        this.contactNumber = contactNumber;
        this.userRole = userRole;
        this.nif = nif;
        this.recruitmentCompany = recruitmentCompany;
        this.registryDate = registryDate;
        this.userEquipments = equipments;
    }


    public User(String firstName, String lastName, int workNumber, LocalDate birthdayDate, String email, String password, LocalDate admissionDate, boolean isActive, String contactNumber, UserRole userRole, String nif, String recruitmentCompany, String registryDate, Set<UserEquipment> equipments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.workNumber = workNumber;
        this.birthdayDate = birthdayDate;
        this.email = email;
        this.password = password;
        this.admissionDate = admissionDate;
        this.isActive = isActive;
        this.contactNumber = contactNumber;
        this.userRole = userRole;
        this.nif = nif;
        this.recruitmentCompany = recruitmentCompany;
        this.registryDate = registryDate;
        this.userEquipments = equipments;
    }

    public User(String firstName, String lastName, LocalDate admissionDate, String email, String nif, int workNumber, String password, String contactNumber, Set<UserEquipment> userEquipments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.admissionDate = admissionDate;
        this.email = email;
        this.nif = nif;
        this.workNumber = workNumber;
        this.password = password;
        this.userRole = UserRole.EMPLOYEE;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.isActive = true;
        this.workStatus = WorkStatus.AVAILABLE;
        this.contactNumber = contactNumber;
        this.userEquipments = userEquipments;
        this.isAvailableForVacation = false;
    }

    public User(Long id, String firstName, String lastName, String nif, int workNumber, LocalDate birthdayDate, WorkStatus workStatus, String recruitmentCompany, String registryDate, LocalDate admissionDate, boolean active, UserRole userRole, String email, String contactNumber, String password, String updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nif = nif;
        this.workNumber = workNumber;
        this.birthdayDate = birthdayDate;
        this.workStatus = workStatus;
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
