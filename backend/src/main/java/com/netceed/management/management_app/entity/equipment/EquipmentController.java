package com.netceed.management.management_app.entity.equipment;

import com.netceed.management.management_app.entity.userEquipment.UserEquipmentDto;
import com.netceed.management.management_app.service.EquipmentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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

    //Return All Equipments StatusEquipment.AVAILABLE
    @GetMapping("/all-available")
    public List<EquipmentDto> getAllEquipmentsAvailable(){
        return equipmentService.getEquipmentsAvailable();
    }

    @GetMapping("/{id}")
    public EquipmentDto getById(@PathVariable Long id){
        return equipmentService.getById(id);
    }

    @PostMapping
    public EquipmentDto createEquipment(@RequestBody @Valid EquipmentDto newEquipment) throws NoSuchFieldException {
        return equipmentService.create(newEquipment);
    }

    /****** Create a equipment object. After assigns the equipment object to the user_id given ******/
    @PostMapping("/{userId}")
    @Transactional
    public UserEquipmentDto createEquipmentWithAssignmentToUser(@RequestBody @Valid EquipmentDto equipment, @PathVariable("userId") Long userId) throws NoSuchFieldException, BadRequestException {
        return equipmentService.createEquipmentForUser(equipment, userId);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid EquipmentDto equipment, @PathVariable Long id){
        equipmentService.update(equipment, id);
    }

    @PutMapping("deactivate/{id}")
    public void delete(@PathVariable Long id){equipmentService.delete(id);}

    //To assign multiple equipments
    @PostMapping("/multipleEquipments")
    public List<EquipmentDto> findEquipmentsByIds(@RequestBody List<Long> equipmentsId){
        return equipmentService.findEquipmentsByIds(equipmentsId);
    }
}
