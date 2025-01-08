package com.netceed.management.management_app.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.shift.Shift;
import com.netceed.management.management_app.entity.userEquipment.UserEquipment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.Set;

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
    Department department,
    @Enumerated(EnumType.STRING)
    WorkStatus workStatus,
    Shift shift,
    @NotNull
    String recruitmentCompany,
    @NotNull
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
    Set<UserEquipment> userEquipments
){
    public UserDto(Long id, String firstName, String lastName, int workNumber, LocalDate birthdayDate, Department department, WorkStatus workStatus, Shift shift, String recruitmentCompany, String registryDate, LocalDate admissionDate, boolean isActive, UserRole userRole, String email, String nif, String contactNumber, String password,
                   boolean isAvailableForVacation, String updatedAt,
                   Set<UserEquipment> userEquipments) {
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
        this.isActive = isActive;
        this.userRole = userRole;
        this.email = email;
        this.nif = nif;
        this.contactNumber = contactNumber;
        this.isAvailableForVacation = isAvailableForVacation;
        this.password = password;
        this.updatedAt = updatedAt;
        this.userEquipments = userEquipments;
    }

    public UserDto setId(Long id){
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setFirstName(String firstName) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setLastName(String lastName) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setWorkNumber(int workNumber) {
            return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                    registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setBirthdayDate(LocalDate birthdayDate) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setBirthdayDate(Department department) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setWorkStatus(WorkStatus workStatus) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setShift(Shift shift) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setRecruitmentCompany(String recruitmentCompany) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setRegistryDate(String registryDate) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setAdmissionDate(LocalDate admissionDate) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setIsActive(boolean value) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, value, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setUserRole(UserRole userRole) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setEmail(String email) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setAvailableForVacation(boolean value) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, value, updatedAt, userEquipments);
    }

    public UserDto setDepartment(Department department) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setContactNumber(String email) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setPassword(String password) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setIsAvailableForVacation(boolean value) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, value, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setUpdatedAt(String updatedAt) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

    public UserDto setUserEquipments(Set<UserEquipment> userEquipments) {
        return new UserDto(id, firstName, lastName, workNumber, birthdayDate, department, workStatus, shift, recruitmentCompany,
                registryDate, admissionDate, isActive, userRole, email, nif, contactNumber, password, isAvailableForVacation, updatedAt, userEquipments);
    }

}
