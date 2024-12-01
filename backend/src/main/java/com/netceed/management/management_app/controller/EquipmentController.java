package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.dto.EquipmentDto;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.service.impl.EquipmentServiceImpl;
import com.netceed.management.management_app.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentServiceImpl equipmentService;
    private final UserServiceImpl userService;

    @GetMapping("/all")
    public ResponseEntity<List<EquipmentDto>> getAll() throws Exception {

        List<EquipmentDto> equipmentFound = equipmentService.getAllEquipments();

        if (equipmentFound.isEmpty())
            throw new Exception("No equipments to retrieve");
        return ResponseEntity.ok(equipmentFound);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDto> getById(@PathVariable Long id){
        if (equipmentService.getById(id) ==  null){
            throw new ResourceNotFoundException("Equipment with the id " + id + " not found ");
        }
        return ResponseEntity.ok(equipmentService.getById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<EquipmentDto> create(@RequestBody @Valid Equipment newEquipment) throws NoSuchFieldException {
        boolean serialNumberAlreadyRegistered = equipmentService.serialNumberExists(newEquipment.getSerialNumber());

        if (serialNumberAlreadyRegistered) {
            throw new NoSuchFieldException("Serial Number " +  newEquipment.getSerialNumber() + " already belong to a equipment");
        }

        return ResponseEntity.ok(equipmentService.create(newEquipment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid EquipmentDto equipment, @PathVariable Long id){

        try{
            Equipment equipmentToUpdate = equipmentService.update(equipment, id);
            return new ResponseEntity<>(equipmentToUpdate, HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        EquipmentDto equipment = equipmentService.getById(id);

        if (equipment == null){
            throw new ResourceNotFoundException("Impossible to delete the resource. The id " + id + " was not found.");
        }

        equipmentService.delete(id);
    }
}
