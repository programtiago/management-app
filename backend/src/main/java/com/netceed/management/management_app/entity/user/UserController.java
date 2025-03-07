package com.netceed.management.management_app.entity.user;

import com.netceed.management.management_app.entity.trackaudit.TrackAuditService;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartmentDto;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipmentDto;
import com.netceed.management.management_app.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@EqualsAndHashCode
public class UserController {

    private final UserService userService;
    private final TrackAuditService trackAuditService;

    /*
    @GetMapping
    public List<UserDto> getAllUsers() throws Exception {
        return userService.getAllUsers();
    }
     */

    @GetMapping
    public UserPageDto getAllUsers(@RequestParam(defaultValue = "0") @PositiveOrZero int page, @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize) throws Exception {
        return userService.getAllUsers(page, pageSize);
    }

    @GetMapping("/active")
    public List<UserDto> getAllUsersActivate() throws Exception {
        return userService.getAllUsersActivate();
    }

    @GetMapping("/not-active")
    public List<UserDto> getAllUsersNotActivate() throws Exception {
        return userService.getAllUsersNotActivate();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @PutMapping("delete/{id}")
    public void delete(@PathVariable Long id){
        UserDto userDto = userService.getById(id);

        if (userDto != null){
            this.deactivateAccount(id);
            trackAuditService.logAction(userDto.id(),"User [ " + userDto.workNumber() + " ] - " + userDto.firstName() + " " + userDto.lastName() + " was deactivated ",  "testUsername", "User");
        }
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid UserDto user, @PathVariable Long id){
        User userObj = userService.update(user, id);

        if (userObj != null){
            trackAuditService.logAction(userObj.getId(), "Updated data of user [ " + userObj.getWorkNumber() + " ] " + userObj.getFirstName() + " " + userObj.getLastName() , "testUsername", "User");
        }
    }

    @PostMapping
    @Transactional
    public UserDto create(@RequestBody @Valid UserDto newUser) throws NoSuchFieldException {
        UserDto userDto = userService.create(newUser);

        return userDto;
    }

    @PostMapping("equipment/{equipmentId}")
    @Transactional
    public UserEquipmentDto createUserWithAssignmentToEquipment(@RequestBody @Valid UserDto newUser, @PathVariable("equipmentId") Long equipmentId) throws NoSuchFieldException, BadRequestException {
        UserEquipmentDto userEquipmentDto = userService.createUserForEquipment(newUser, equipmentId);

        if (userEquipmentDto != null){
            trackAuditService.logAction(userEquipmentDto.id(), "Created user [ " + userEquipmentDto.user().getWorkNumber() + " ] " + userEquipmentDto.user().getFirstName() + " " + userEquipmentDto.user().getLastName() + " and assigned equipment with serial " + userEquipmentDto.equipment().getSerialNumber() , "testUsername", "UserEquipment");
        }

        return userEquipmentDto;
    }

    @PostMapping("department/{departmentId}")
    @Transactional
    public UserDepartmentDto createUserWithAssignmentToDepartment(@RequestBody @Valid UserDto newUser, @PathVariable("departmentId") Long departmentId) throws NoSuchFieldException, BadRequestException {
        UserDepartmentDto userDepartmentDto = userService.createUserForDepartment(newUser, departmentId);

        if (userDepartmentDto != null){
            trackAuditService.logAction(userDepartmentDto.id(), "Created user [ " + userDepartmentDto.user().getWorkNumber() + " ] " + userDepartmentDto.user().getFirstName() + " " + userDepartmentDto.user().getLastName() + " and assigned to department with code " + userDepartmentDto.department().getCodeValue() , "testUsername", "UserDepartment");
        }

        return userDepartmentDto;
    }

    //Method responsible for changing status of the user   boolean true -> false
    @PutMapping("/deactivate/{id}")
    public UserDto deactivateAccount(@PathVariable Long id){
        return userService.deactivateAccount(id);
    }

    //Method responsible for changing status of the user   boolean false -> true
    @PutMapping("/activate/{id}")
    public UserDto activateAccount(@PathVariable Long id){
        UserDto userDto = userService.activateAccount(id);

        if (userDto != null){
            trackAuditService.logAction(userDto.id(), "Activated user [ " + userDto.workNumber() + " ] " + userDto.firstName() + " " + userDto.lastName(), "testUsername", "User");
        }

        return userDto;
    }
    @GetMapping("/department/{departmentId}")
    public Set<User> getUsersByDepartmentId(@PathVariable Long departmentId) {
        return userService.getEmployeesByDepartmentId(departmentId);
    }

    /************************Return a List of users available to assign to a department *********************/
    @GetMapping("/available-for-department")
    public List<UserDto> getUsersAvailableAssignToDepartment(){
        return userService.getUsersAvailableAssignToDepartment();
    }


    @GetMapping("/search")
    public List<UserDto> searchUser(@RequestParam("keyword") String keyword){
        return userService.search(keyword);
    }
}
