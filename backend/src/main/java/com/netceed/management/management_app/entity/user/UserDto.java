package com.netceed.management.management_app.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.shift.Shift;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartment;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public record UserDto (
    Long id,
    @NotBlank(message = "The First Name is mandatory")
    String firstName,
    @NotBlank(message = "The Last Name is mandatory")
    String lastName,
    @Range(min = 30000, max = 100000, message = "The Work Number must be between 3000 and 100000")
    int workNumber,
    LocalDate birthdayDate,
    @Enumerated(EnumType.STRING)
    WorkStatus workStatus,
    Shift shift,
    @NotNull
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
    @Length(min = 9, max = 9)
    String nif,
    @NotBlank(message = "The Contact Number is mandatory")
    @Size(min = 9, max = 9)
    String contactNumber,
    @NotBlank(message = "The Password is mandatory")
    String password,
    boolean isAvailableForVacation,
    String updatedAt,
    Set<UserEquipment> userEquipments,
    Set<UserDepartment> userDepartments,
    boolean userAlreadyOnDepartment
){

    public UserDto(Long id, String firstName, String lastName, int workNumber, LocalDate birthdayDate,WorkStatus workStatus, Shift shift, String recruitmentCompany, String registryDate, LocalDate admissionDate, boolean isActive, UserRole userRole, String email, String nif, String contactNumber, String password,
                   boolean isAvailableForVacation, String updatedAt, Set<UserEquipment> userEquipments,  Set<UserDepartment> userDepartments, boolean userAlreadyOnDepartment) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workNumber = workNumber;
        this.birthdayDate = birthdayDate;
        this.workStatus = workStatus;
        this.userDepartments = userDepartments;
        this.shift = shift;
        this.recruitmentCompany = recruitmentCompany;
        this.registryDate = registryDate;
        this.admissionDate = admissionDate;
        this.isActive = isActive;
        this.userRole = userRole;
        this.email = email;
        this.nif = nif;
        this.contactNumber = contactNumber;
        this.isAvailableForVacation = isAvailableForVacation;
        this.password = password;
        this.updatedAt = updatedAt;
        this.userEquipments = userEquipments;
        this.userAlreadyOnDepartment = userAlreadyOnDepartment;
    }

    public static UserDto createNewUserAssignEquipment(Long id, String firstName, String lastName, int workNumber, LocalDate birthdayDate, Shift shift, String recruitmentCompany, LocalDate admissionDate, String email, String nif, String contactNumber, String password, String updatedAt, Set<UserEquipment> userEquipments,
                                                       Set<UserDepartment> userDepartments, boolean userAlreadyOnDepartment){
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, WorkStatus.AVAILABLE, shift, recruitmentCompany, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                admissionDate, true, UserRole.EMPLOYEE, email, nif, contactNumber, password, false, updatedAt, userEquipments, null, userAlreadyOnDepartment);
    }

    public static UserDto createNewUserAssignDepartment(Long id, String firstName, String lastName, int workNumber, LocalDate birthdayDate, Shift shift, String recruitmentCompany, LocalDate admissionDate, String email, String nif, String contactNumber, String password, String updatedAt,
                                                       Set<UserDepartment> userDepartments){
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, WorkStatus.AVAILABLE, shift, recruitmentCompany, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                admissionDate, true, UserRole.EMPLOYEE, email, nif, contactNumber, password, false, updatedAt, null, userDepartments, true);
    }

    public static UserDto createNewUserWithNoAssign(Long id, String firstName, String lastName, int workNumber, LocalDate birthdayDate, String recruitmentCompany, LocalDate admissionDate, String email, String nif, String contactNumber, String password){
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, WorkStatus.AVAILABLE, null, recruitmentCompany, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                admissionDate, true, UserRole.EMPLOYEE, email, nif, contactNumber, password, false, null, null, null, false);
    }

    public UserDto setId(Long id){
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments, userDepartments, userAlreadyOnDepartment);
    }

    public UserDto setShift(Shift shift) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments, userDepartments, userAlreadyOnDepartment);
    }

    public UserDto setIsActive(boolean value) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, value, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments, userDepartments, userAlreadyOnDepartment);
    }

    public UserDto setDepartment(Department department) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments, userDepartments, userAlreadyOnDepartment);
    }
}
