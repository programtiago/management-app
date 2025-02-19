package com.netceed.management.management_app.entity.user;

import com.netceed.management.management_app.entity.trackaudit.TrackAuditService;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartmentDto;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipmentDto;
import com.netceed.management.management_app.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers() throws Exception {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid UserDto user, @PathVariable Long id){
        userService.update(user, id);
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserDto newUser) throws NoSuchFieldException {
        return userService.create(newUser);
    }

    @PostMapping("equipment/{equipmentId}")
    @Transactional
    public UserEquipmentDto createUserWithAssignmentToEquipment(@RequestBody @Valid UserDto newUser, @PathVariable("equipmentId") Long equipmentId) throws NoSuchFieldException, BadRequestException {
        return userService.createUserForEquipment(newUser, equipmentId);
    }

    @PostMapping("department/{departmentId}")
    @Transactional
    public UserDepartmentDto createUserWithAssignmentToDepartment(@RequestBody @Valid UserDto newUser, @PathVariable("departmentId") Long departmentId) throws NoSuchFieldException, BadRequestException {
        return userService.createUserForDepartment(newUser, departmentId);
    }

    //Method responsible for changing status of the user   boolean true -> false
    @PutMapping("/deactivate/{id}")
    public UserDto deactivateAccount(@PathVariable Long id){
        return userService.deactivateAccount(id);
    }

    //Method responsible for changing status of the user   boolean false -> true
    @PutMapping("/activate/{id}")
    public UserDto activateAccount(@PathVariable Long id){
        return userService.activateAccount(id);
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
}
