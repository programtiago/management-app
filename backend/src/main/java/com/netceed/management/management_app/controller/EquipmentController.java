package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.dto.EquipmentDto;
import com.netceed.management.management_app.service.EquipmentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping
    public List<EquipmentDto> getAll(){
        return equipmentService.getAllEquipments();
    }

    @GetMapping("/all-available")
    public List<EquipmentDto> getAllEquipmentsAvailable(){
        return equipmentService.getEquipmentsAvailable();
    }

    @GetMapping("/{id}")
    public EquipmentDto getById(@PathVariable Long id){
        return equipmentService.getById(id);
    }

    @PostMapping
    public EquipmentDto createEquipment(@RequestBody @Valid EquipmentDto newEquipment){
        return equipmentService.create(newEquipment);
    }

    /****** Create a equipment object. After assigns the equipment object to the user_id given ******/
    @PostMapping("/{userId}")
    @Transactional
    public EquipmentDto createEquipmentWithAssignmentToUser(@RequestBody @Valid EquipmentDto newEquipment, @PathVariable("userId") Long userId) throws NoSuchFieldException {
        return equipmentService.createEquipmentForUser(newEquipment, userId);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid EquipmentDto equipment, @PathVariable Long id){
        equipmentService.update(equipment, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        equipmentService.delete(id);
    }

    //To assign multiple equipments
    @PostMapping("/multipleEquipments")
    public List<EquipmentDto> findEquipmentsByIds(@RequestBody List<Long> equipmentsId){
        return equipmentService.findEquipmentsByIds(equipmentsId);
    }
}
