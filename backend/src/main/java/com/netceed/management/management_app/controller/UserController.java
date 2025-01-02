package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.dto.UserDto;
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
    public User create(@RequestBody @Valid UserDto newUser) throws NoSuchFieldException {
        return userService.create(newUser);
    }

    //Method responsible for changing status of the user   boolean true -> false
    @PostMapping("/deactivate/{id}")
    public void deactivateAccount(@PathVariable Long id) throws Exception{
        userService.deactivateAccount(id);
    }

    //Method responsible for changing status of the user   boolean false -> true
    @PostMapping("/activate/{id}")
    public void activateAccount(@PathVariable Long id) throws Exception{
        userService.activateAccount(id);
    }

    @GetMapping("/department/{id}")
    public List<UserDto> getUsersByDepartment(@PathVariable Long id){
        return userService.getUsersByDepartment(id);
    }
}
