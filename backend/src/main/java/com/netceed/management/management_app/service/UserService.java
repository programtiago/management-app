package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.equipment.EquipmentDto;
import com.netceed.management.management_app.entity.equipment.EquipmentMapper;
import com.netceed.management.management_app.entity.user.User;
import com.netceed.management.management_app.entity.user.UserDto;
import com.netceed.management.management_app.entity.user.UserMapper;
import com.netceed.management.management_app.exception.BirthayDateException;
import com.netceed.management.management_app.exception.EmailAlreadyExistsException;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;

    private final UserMapper userMapper;

    private final EquipmentService equipmentService;
    private final UserEquipmentService userEquipmentService;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::toDto)
                .collect(Collectors.toList());
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
                    user.setActive(userUpdate.isActive());
                    user.setWorkNumber(userUpdate.workNumber());
                    user.setContactNumber(userUpdate.contactNumber());
                    user.setEmail(userUpdate.email());
                    user.setDepartment(userUpdate.department());
                    user.setPassword(userUpdate.password());
                    user.setWorkStatus(userUpdate.workStatus());
                    user.setAvailableForVacation(userUpdate.isAvailableForVacation());
                    user.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Operation failed because the resource with the id " + id + " doesn't exist."));
    }

    public UserDto create(UserDto newUser){
        boolean workNumberAlreadyExists = checkIfGivenWorkNumberExists(newUser.workNumber());
        boolean emailAlreadyExists = findEmailIfAlreadyExists(newUser.email());
        boolean birthdayDateGivenIsValid = checkIfGivenBirthdayDateIsValid(newUser.birthdayDate());

        if (workNumberAlreadyExists) {
            throw new IllegalArgumentException("Work Number " +  newUser.workNumber() + " already registered");
        }
        if (emailAlreadyExists){
            throw new EmailAlreadyExistsException("Email " +  newUser.email() + " already registered");
        }

        if (!birthdayDateGivenIsValid){
            throw new BirthayDateException("Birthday Date given indicate the user doesn't have 18 years or more");
        }

        if (newUser.department() != null){
            newUser.department().setTotalEmployees(departmentRepository.getTotalOfEmployeesByDepartment(newUser.id()) + 1);
        }

        User newUserEntity = userMapper.toEntity(newUser);
        userRepository.save(newUserEntity);

        return userMapper.toDto(newUserEntity);
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
        } else {
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

    public List<UserDto> getUsersByDepartment(Long id) {
        return userRepository.findAll().stream()
                .filter(user -> user.getDepartment().getId().equals(id)).map(userMapper::toDto)
                .toList();
    }

    /****** Create a user object. After assigns the user object to the equipment_id given ******/
    public UserDto createUserForEquipment(UserDto newUser, Long equipmentId) {
        EquipmentDto equipmentDto = equipmentService.getById(equipmentId);

        if (equipmentDto.id() != null) {
            create(newUser);
        }

        userEquipmentService.assignEquipmentToUser(newUser.id(), equipmentDto.id());

        return userMapper.fromDtoToDto(newUser);
    }
}
