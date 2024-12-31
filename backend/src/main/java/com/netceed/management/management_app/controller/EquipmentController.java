package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.entity.dto.EquipmentDto;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.mapper.EquipmentMapper;
import com.netceed.management.management_app.entity.mapper.UserMapper;
import com.netceed.management.management_app.enums.StatusEquipment;
import com.netceed.management.management_app.exception.EquipmentHasAssigmentException;
import com.netceed.management.management_app.service.impl.EquipmentService;
import com.netceed.management.management_app.service.impl.UserEquipmentService;
import com.netceed.management.management_app.service.impl.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final UserService userService;
    private final UserEquipmentService userEquipmentService;
    private final UserMapper userMapper;
    private final EquipmentMapper equipmentMapper;

    @GetMapping
    public List<EquipmentDto> getAll() throws Exception {

        List<EquipmentDto> equipmentFound = equipmentService.getAllEquipments();

        if (equipmentFound.isEmpty())
            throw new Exception("No equipments to retrieve");
        return equipmentFound;
    }

    @GetMapping("/all-available")
    public List<EquipmentDto> getAllEquipmentsAvailable(){
        List<EquipmentDto> equipmentsAvailableFound = equipmentService.getEquipmentsAvailable();

        if (equipmentsAvailableFound.isEmpty()){
            throw new RuntimeException("No equipments available to list");
        }

        return equipmentsAvailableFound;
    }


    @GetMapping("/{id}")
    public EquipmentDto getById(@PathVariable Long id){
        return equipmentService.getById(id);
    }

    @PostMapping
    public Equipment createEquipment(@RequestBody @Valid EquipmentDto newEquipment){
        return equipmentService.create(newEquipment);
    }

    /****** Create a equipment object. After assigns the equipment object to the user_id given ******/
    @PostMapping("/new/{userId}")
    @Transactional
    public Equipment createEquipmentWithAssignmentToUser(@RequestBody @Valid EquipmentDto newEquipment, @PathVariable("userId") Long userId) throws NoSuchFieldException {
        UserEquipment userEquipment = new UserEquipment();
        UserDto userFound = userService.getById(userId);

        boolean serialNumberAlreadyRegistered = equipmentService.serialNumberExists(newEquipment.serialNumber());

        if (serialNumberAlreadyRegistered) {
            throw new NoSuchFieldException("Serial Number " +  newEquipment.serialNumber() + " already belong to a equipment");
        }

        if (userFound.id() != null){
            newEquipment.setStatusEquipment(StatusEquipment.IN_USE);
            userEquipment.setEquipment(equipmentMapper.toEntity(newEquipment));
            userEquipment.setAssignedDate(LocalDateTime.now());
            userEquipment.setUser(userMapper.toEntity(userFound));
            userEquipment.setComments(newEquipment.description() + " was assigned at " + userEquipment.getAssignedDate());
        }

        equipmentService.create(newEquipment);

        userEquipmentService.assignEquipmentToUser(userId, newEquipment.id());

        return equipmentService.createEquipmentForUser(newEquipment, userId);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid EquipmentDto equipment, @PathVariable Long id){
        equipmentService.update(equipment, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        EquipmentDto equipment = equipmentService.getById(id);

        if (equipment == null){
            throw new EntityNotFoundException("Impossible to delete the resource. The id " + id + " was not found.");
        }else if (!equipment.usersEquipments().isEmpty()){
            throw new EquipmentHasAssigmentException("Impossible to delete the resource. The resource has one or more assignments");
        }

        equipmentService.delete(id);
    }

    //To assign multiple equipments
    @PostMapping("/multipleEquipments")
    public List<EquipmentDto> findEquipmentsByIds(@RequestBody List<Long> equipmentsId){
        return equipmentService.findEquipmentsByIds(equipmentsId);
    }
}
