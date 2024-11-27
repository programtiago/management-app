package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.Department;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.dto.DepartmentDto;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.service.impl.DepartmentServiceImpl;
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

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() throws Exception {
        List<DepartmentDto> departmentsFound = departmentService.getAllDepartments();

        if (departmentsFound.isEmpty())
            throw new Exception("No departments to retrieve.");
        return ResponseEntity.ok(departmentsFound);
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
}
