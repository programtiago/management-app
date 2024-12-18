package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.Department;
import com.netceed.management.management_app.entity.dto.DepartmentDto;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.mapper.DepartmentMapper;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.service.impl.DepartmentServiceImpl;
import com.netceed.management.management_app.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;
    private final UserServiceImpl userService;
    private final DepartmentMapper departmentMapper;

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() throws Exception {
        List<DepartmentDto> departmentsFound = departmentService.getAllDepartments();

        if (departmentsFound.isEmpty())
            throw new Exception("No departments to retrieve.");
        return ResponseEntity.ok(departmentsFound);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getById(@PathVariable Long id){
        if (departmentService.getById(id) == null){
            throw new ResourceNotFoundException("Department with the id " + id + " not found ");
        }
        return ResponseEntity.ok(departmentService.getById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<DepartmentDto> create(@RequestBody @Valid Department newDepartment){
        //Each department code has to be different - Department code associate only one department ?? maybe in future this changes
        boolean codeDepartmentAlreadyExists = departmentService.valueDepartmentExists(newDepartment.getValue());

        if (codeDepartmentAlreadyExists){
            throw new IllegalArgumentException("Department Value " + newDepartment.getValue() + " already exists.");
        }

        return ResponseEntity.ok(departmentService.create(newDepartment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid DepartmentDto department, @PathVariable Long id){
        try{
            Department departmentToUpdate = departmentService.update(department, id);
            return new ResponseEntity<>(departmentToUpdate, HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        DepartmentDto department = departmentService.getById(id);

        if (department == null){
            throw new ResourceNotFoundException("Impossible to delete the resource. The id " + id + " was not found.");
        }

        departmentService.deleteById(id);
    }

    @GetMapping("/totalEmployees/{id}")
    public int getTotalEmployeesForDepartment(@PathVariable Long id){
        DepartmentDto department = departmentService.getById(id);

        if (department == null){
            throw new ResourceNotFoundException("Impossible to delete the resource. The id " + id + " was not found.");
        }

        return departmentService.retrieveTotalOfEmployees(id);
    }
}
