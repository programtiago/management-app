package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.UserDto;
import com.netceed.management.management_app.exception.BirthayDateException;
import com.netceed.management.management_app.exception.EmailAlreadyExistsException;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() throws Exception {
        List<UserDto> usersFound = userService.getAllUsers();

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

        System.out.println(newUser.getBirthdayDate());

        boolean workNumberAlreadyExists = userService.workNumberExists(newUser.getWorkNumber());
        boolean emailAlreadyExists = userService.emailAlreadyExists(newUser.getEmail());
        boolean birthdayDateGivenIsValid = userService.birthdayDateIsValid(newUser.getBirthdayDate());

        if (workNumberAlreadyExists) {
            throw new NoSuchFieldException("Work Number " +  newUser.getWorkNumber() + " already registered");
        }
        if (emailAlreadyExists){
            throw new EmailAlreadyExistsException("Email " +  newUser.getEmail() + " already registered");
        }
        if (newUser.getAdmissionDate() == null){
            newUser.setAdmissionDate(LocalDate.now()); //if the user doesn't send admission date, will be created a instance automatically
        }
        if (!birthdayDateGivenIsValid){
            throw new BirthayDateException("Birthday Date given indicate the user doesn't have 18 years or more");
        }

        return ResponseEntity.ok(userService.create(newUser));
    }

    //Method responsible for changing status of the user   boolean true -> false
    @PostMapping("/deactivate/{id}")
    public void deactivateAccount(@PathVariable Long id) throws Exception{
        Optional<User> user = userService.getById(id);

        if (user.isPresent()){
            if (user.get().isActive()){
                try{
                    userService.deactivateAccount(id);
                }catch (Exception e){
                    throw new Exception("Something went wrong. Please try again");
                }
            }else{
                throw new IllegalArgumentException("User already deactivated. Impossible to active with id " + id);
            }
        }else{
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }

    //Method responsible for changing status of the user   boolean false -> true
    @PostMapping("/activate/{id}")
    public void activateAccount(@PathVariable Long id) throws Exception{
        Optional<User> user = userService.getById(id);

        if (user.isPresent()){
            if (!user.get().isActive()){
                try{
                    userService.activateAccount(id);
                }catch (Exception e){
                    throw new Exception("Something went wrong. Please try again");
                }
            }else{
                throw new IllegalArgumentException("User already activated. Impossible to active with id " + id);
            }
        }else{
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }
}
