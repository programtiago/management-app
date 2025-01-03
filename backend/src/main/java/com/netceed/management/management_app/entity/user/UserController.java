package com.netceed.management.management_app.entity.user;

import com.netceed.management.management_app.service.UserService;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@EqualsAndHashCode
public class UserController {

    private final UserService userService;

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

    @GetMapping("/department/{id}")
    public List<UserDto> getUsersByDepartment(@PathVariable Long id){
        return userService.getUsersByDepartment(id);
    }
}
