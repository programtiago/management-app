package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.exception.EmailAlreadyExistsException;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() throws Exception {
        List<User> usersFound = userService.getAllUsers();

        if (usersFound.isEmpty())
            throw new Exception("No users to retrieve");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable Long id){
        if (userService.getById(id).isEmpty()){
            throw new ResourceNotFoundException("User with the id " + id + " not found ");
        }
        return ResponseEntity.ok(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<User> user = userService.getById(id);

        if (user.isEmpty()){
            throw new ResourceNotFoundException("Impossible to delete the resource. The id " + id + " was not found.");
        }

        userService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid User user, @PathVariable Long id){

        try{
            User userToUpdate = userService.update(user, id);
            return new ResponseEntity<>(userToUpdate, HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<User> create(@RequestBody @Valid User newUser) throws NoSuchFieldException {
        boolean workNumberAlreadyExists = userService.workNumberExists(newUser.getWorkNumber());
        boolean emailAlreadyExists = userService.emailAlreadyExists(newUser.getEmail());

        if (workNumberAlreadyExists)
            throw new NoSuchFieldException("Work Number " +  newUser.getWorkNumber() + " already registered");
        else if (emailAlreadyExists)
            throw new EmailAlreadyExistsException("Email " +  newUser.getEmail() + " already registered");
        return ResponseEntity.ok(userService.create(newUser));
    }


}
