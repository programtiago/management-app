package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.entity.dto.EquipmentDto;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.mapper.UserMapper;
import com.netceed.management.management_app.enums.StatusEquipment;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.service.impl.EquipmentServiceImpl;
import com.netceed.management.management_app.service.impl.UserEquipmentServiceImpl;
import com.netceed.management.management_app.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentServiceImpl equipmentService;
    private final UserServiceImpl userService;
    private final UserEquipmentServiceImpl userEquipmentService;
    private final UserMapper userMapper;

    @GetMapping("/all")
    public ResponseEntity<List<EquipmentDto>> getAll() throws Exception {

        List<EquipmentDto> equipmentFound = equipmentService.getAllEquipments();

        if (equipmentFound.isEmpty())
            throw new Exception("No equipments to retrieve");
        return ResponseEntity.ok(equipmentFound);
    }

    @GetMapping("/all-available")
    public ResponseEntity<List<EquipmentDto>> getAllEquipmentsAvailable(){
        List<EquipmentDto> equipmentsAvailableFound = equipmentService.getEquipmentsAvailable();

        if (equipmentsAvailableFound.isEmpty()){
            throw new RuntimeException("No equipments available to list");
        }

        return ResponseEntity.ok(equipmentsAvailableFound);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDto> getById(@PathVariable Long id){
        if (equipmentService.getById(id) ==  null){
            throw new ResourceNotFoundException("Equipment with the id " + id + " not found ");
        }
        return ResponseEntity.ok(equipmentService.getById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<EquipmentDto> createEquipment(@RequestBody @Valid Equipment newEquipment){
        return ResponseEntity.ok(equipmentService.create(newEquipment));
    }

    /****** Create a equipment object. After assigns the equipment object to the user_id given ******/
    @PostMapping("/new/{userId}")
    @Transactional
    public ResponseEntity<EquipmentDto> createEquipmentWithAssignmentToUser(@RequestBody @Valid Equipment newEquipment, @PathVariable("userId") Long userId) throws NoSuchFieldException {
        UserEquipment userEquipment = new UserEquipment();
        UserDto userFound = userService.getById(userId);

        boolean serialNumberAlreadyRegistered = equipmentService.serialNumberExists(newEquipment.getSerialNumber());

        if (serialNumberAlreadyRegistered) {
            throw new NoSuchFieldException("Serial Number " +  newEquipment.getSerialNumber() + " already belong to a equipment");
        }

        if (userFound.id() != null){
            newEquipment.setStatusEquipment(StatusEquipment.IN_USE);
            userEquipment.setEquipment(newEquipment);
            userEquipment.setAssignedDate(LocalDateTime.now());
            userEquipment.setUser(userMapper.toEntity(userFound));
            userEquipment.setComments(newEquipment.getDescription() + " was assigned at " + userEquipment.getAssignedDate());
        }

        equipmentService.create(newEquipment);

        userEquipmentService.assignEquipmentToUser(userId, newEquipment.getId());

        return ResponseEntity.ok(equipmentService.createEquipmentForUser(newEquipment, userId));
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

    @PostMapping("/multipleEquipments")
    public ResponseEntity<List<EquipmentDto>> findEquipmentsByIds(@RequestBody List<Long> equipmentsId){
        return ResponseEntity.ok(equipmentService.findEquipmentsByIds(equipmentsId));
    }
}
