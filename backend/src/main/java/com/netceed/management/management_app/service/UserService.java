package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.department.DepartmentDto;
import com.netceed.management.management_app.entity.department.DepartmentMapper;
import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.equipment.EquipmentDto;
import com.netceed.management.management_app.entity.equipment.EquipmentMapper;
import com.netceed.management.management_app.entity.trackaudit.TrackAuditService;
import com.netceed.management.management_app.entity.user.*;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartmentDto;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipment;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipmentDto;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.repository.EquipmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;
    private final DepartmentRepository departmentRepository;

    private final TrackAuditService trackAuditService;

    private final UserMapper userMapper;
    private final EquipmentMapper equipmentMapper;
    private final DepartmentMapper departmentMapper;

    private final UserEquipmentService userEquipmentService;
    private final UserDepartmentService userDepartmentService;

    public UserPageDto getAllUsers(int page, int pageSize){
        Page<User> pageUser = userRepository.findAll(PageRequest.of(page, pageSize));
        List<UserDto> users = pageUser.get().map(userMapper::toDto).toList();

        return new UserPageDto(users, pageUser.getTotalElements(), pageUser.getTotalPages());
    }

    public UserPageDto getAllUsersActivate(int page, int pageSize) {
        Page<User> pageUsersActive = userRepository.findByUsersActive(PageRequest.of(page, pageSize));
        List<UserDto> users = pageUsersActive.get().map(userMapper::toDto).toList();

        return new UserPageDto(users, pageUsersActive.getTotalElements(), pageUsersActive.getTotalPages());
    }

    public UserPageDto getAllUsersNotActivate(int page, int pageSize) {
        Page<User> pageUsersNotActive = userRepository.findByUsersNotActive(PageRequest.of(page, pageSize));
        List<UserDto> users = pageUsersNotActive.get().map(userMapper::toDto).toList();

        return new UserPageDto(users, pageUsersNotActive.getTotalElements(), pageUsersNotActive.getTotalPages());
    }

    public UserDto getById(Long id){
        return userRepository.findById(id).map(userMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("User resource not found with id " + id));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User update(UserDto userUpdate, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userUpdate.firstName());
                    user.setLastName(userUpdate.lastName());
                    user.setUserRole(userUpdate.userRole());
                    user.setWorkNumber(userUpdate.workNumber());
                    user.setNif(userUpdate.nif());
                    user.setRegistryDate(user.getRegistryDate());
                    user.setContactNumber(userUpdate.contactNumber());
                    user.setBirthdayDate(userUpdate.birthdayDate());
                    user.setRecruitmentCompany(userUpdate.recruitmentCompany());
                    user.setEmail(userUpdate.email());
                    user.setActive(userUpdate.isActive());
                    user.setUserDepartments(userUpdate.userDepartments());
                    user.setPassword(userUpdate.password());
                    user.setWorkStatus(userUpdate.workStatus());
                    user.setAvailableForVacation(userUpdate.isAvailableForVacation());
                    user.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Operation failed because the resource with the id " + id + " doesn't exist."));
    }

    public UserDto create(UserDto newUser){
        User user = userMapper.toEntity(newUser);
        User savedUser = userRepository.save(user);

        if (savedUser.getId() != null){
            trackAuditService.logAction(user.getId(), "Created user [ " + user.getWorkNumber() + " ] " + user.getFirstName() + " " + user.getLastName() , "testUsername", "User");
        }

        return userMapper.toDto(savedUser);
    }

    public boolean checkIfGivenWorkNumberExists(int workNumber) {
        return userRepository.findByWorkNumber(workNumber).isPresent();
    }

    public boolean findEmailIfAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean checkIfGivenBirthdayDateIsValid(LocalDate birthdayDate) {
        int yearOfToday = LocalDate.now().getYear();
        int yearOfBirthday = birthdayDate.getYear();

        return (yearOfToday - yearOfBirthday) >= 18;
    }

    public UserDto deactivateAccount(Long id){
        User userToDeactivate = userRepository.findById(id).orElseThrow();

        if (userToDeactivate.isActive()) {
            try {
                userToDeactivate.setActive(false);
                userRepository.save(userToDeactivate);
            } catch (Exception e) {
                new BadRequestException("Something went wrong. Please try again");
            }
        }else{
            throw new IllegalArgumentException("User already deactivated. Impossible to active with id " + id);
        }

        return userMapper.toDto(userToDeactivate);
    }

    public UserDto activateAccount(Long id){
        User userToActivate = userRepository.findById(id).orElseThrow();

        if (!userToActivate.isActive()) {
            try {
                userToActivate.setActive(true);
                userRepository.save(userToActivate);
            } catch (Exception e) {
                new BadRequestException("Something went wrong. Please try again");
            }
        } else {
            throw new IllegalArgumentException("User already activated. Impossible to active with id " + id);
        }

        return userMapper.toDto(userToActivate);
    }


    /****** Create a user object. After assigns the user object to the equipment_id given ******/
    //public UserDto createUserForEquipment(UserDto newUser, Long equipmentId) throws IllegalArgumentException {
    public UserEquipmentDto createUserForEquipment(UserDto newUser, Long equipmentId) throws IllegalArgumentException {
       Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow();
       EquipmentDto equipmentFound = equipmentMapper.toDto(equipment);

       boolean workNumberAlreadyExists = userRepository.findByWorkNumber(newUser.workNumber()).isPresent();
       boolean emailAlreadyExists = userRepository.findByEmail(newUser.email()).isPresent();

       if (workNumberAlreadyExists){
           throw new IllegalArgumentException("Work Number " +  newUser.workNumber() + " already belong to a user");
       }
       if (emailAlreadyExists){
           throw new IllegalArgumentException("Email " +  newUser.email() + " already belong to a user");
       }

       User savedUser = new User();
       if (equipmentFound.id() != null){
           UserDto userToSave = UserDto.createNewUserAssignEquipment(newUser.id(), newUser.firstName(), newUser.lastName(), newUser.workNumber(), newUser.birthdayDate(), newUser.recruitmentCompany(), newUser.admissionDate(),
                   newUser.isActive(), newUser.email(), newUser.nif(), newUser.contactNumber(), newUser.password(), newUser.updatedAt(), newUser.userEquipments(), newUser.userDepartments(), newUser.userAlreadyOnDepartment(),
                   newUser.perseusLogIn(), newUser.sageLogIn());

           savedUser = userRepository.save(userMapper.toEntity(userToSave));
       }

       return userEquipmentService.assignEquipmentToUser(savedUser.getId(), equipmentId);
    }

    /****** Create a user object. After assigns the user object to the department_id given ******/
    public UserDepartmentDto createUserForDepartment(UserDto newUser, Long departmentId){
        Department department = departmentRepository.findById(departmentId).orElseThrow();
        DepartmentDto departmentFound = departmentMapper.toDto(department);

        boolean workNumberAlreadyExists = userRepository.findByWorkNumber(newUser.workNumber()).isPresent();
        boolean emailAlreadyExists = userRepository.findByEmail(newUser.email()).isPresent();

        if (workNumberAlreadyExists){
            throw new IllegalArgumentException("Work Number " +  newUser.workNumber() + " already belong to a user");
        }
        if (emailAlreadyExists){
            throw new IllegalArgumentException("Email " +  newUser.email() + " already belong to a user");
        }

        User savedUser = new User();
        if (departmentFound.id() != null){
            UserDto userToSave = UserDto.createNewUserAssignDepartment(newUser.id(), newUser.firstName(), newUser.lastName(), newUser.workNumber(), newUser.birthdayDate(),
                    newUser.recruitmentCompany(), newUser.admissionDate(), newUser.email(), newUser.nif(), newUser.contactNumber(), newUser.password(), newUser.updatedAt(),
                    newUser.userDepartments(), newUser.perseusLogIn(), newUser.sageLogIn());

            savedUser = userRepository.save(userMapper.toEntity(userToSave));
        }

        return userDepartmentService.assignDepartmentToUser(savedUser.getId(), departmentId);
    }

    public Set<User> getEmployeesByDepartmentId(Long departmentId) {
        return userRepository.findAllByDepartmentId(departmentId);
    }

    public List<UserDto> getUsersAvailableAssignToDepartment(){
        List<User> users = userRepository.findByUserAlreadyOnDepartmentTrue();

        return userMapper.convertListUserToDto(users);
    }

    public UserPageDto search(String query, int page, int pageSize){
        Page<User> pageUserSearchedResults = userRepository.findByKeyword(query, PageRequest.of(page, pageSize));
        List<UserDto> users = pageUserSearchedResults.get().map(userMapper::toDto).toList();

        return new UserPageDto(users, pageUserSearchedResults.getTotalElements(), pageUserSearchedResults.getTotalPages());
    }

    public UserPageDto filterByUserRole(UserRole userRole, int page, int pageSize){
        Page<User> pageUserFilteredByRole = userRepository.findByUserRole(userRole, PageRequest.of(page, pageSize));
        List<UserDto> users = pageUserFilteredByRole.get().map(userMapper::toDto).toList();

        return new UserPageDto(users, pageUserFilteredByRole.getTotalElements(), pageUserFilteredByRole.getTotalPages());
    }


}
