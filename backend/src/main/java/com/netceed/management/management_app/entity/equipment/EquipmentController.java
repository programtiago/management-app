package com.netceed.management.management_app.entity.equipment;

import com.netceed.management.management_app.entity.trackaudit.TrackAuditService;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipmentDto;
import com.netceed.management.management_app.service.EquipmentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final TrackAuditService trackAuditService;

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
        EquipmentDto equipmentDto = equipmentService.create(newEquipment);

        if (equipmentDto != null){
            trackAuditService.logAction(Collections.singletonList(equipmentDto.id()), "Created equipment with serial " + equipmentDto.serialNumber(), "testUsername", "Equipment");
        }

        return equipmentDto;
    }

    /****** Create a equipment object. After assigns the equipment object to the user_id given ******/
    @PostMapping("/{userId}")
    @Transactional
    public UserEquipmentDto createEquipmentWithAssignmentToUser(@RequestBody @Valid EquipmentDto equipment, @PathVariable("userId") Long userId) throws NoSuchFieldException, BadRequestException {
        UserEquipmentDto userEquipmentDto = equipmentService.createEquipmentForUser(equipment, userId);

        if (userEquipmentDto != null){
            trackAuditService.logAction(Collections.singletonList(userEquipmentDto.id()), "Created equipment with serial " + userEquipmentDto.equipment().getSerialNumber() + " and assigned to [ " + userEquipmentDto.user().getWorkNumber() + "] - " + userEquipmentDto.user().getFirstName() + " " + userEquipmentDto.user().getLastName(), "testUsername", "UserEquipment");
        }

        return userEquipmentDto;
    }

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid EquipmentDto equipment, @PathVariable Long id){
        Equipment equipmentObj = equipmentService.update(equipment, id);

        if (equipmentObj != null){
            trackAuditService.logAction(Collections.singletonList(equipmentObj.getId()), "Updated equipment with serial " + equipmentObj.getSerialNumber(), "testUsername", "UserEquipment");
        }
    }

    @PutMapping("deactivate/{id}")
    public void delete(@PathVariable Long id){
        EquipmentDto equipmentDto = equipmentService.getById(id);

        if (equipmentDto != null) {
            equipmentService.delete(id);
            trackAuditService.logAction(Collections.singletonList(equipmentDto.id()), "Deleted equipment with serial ", "testUsername", "Equipment");
        }
    }

    //To assign multiple equipments
    @PostMapping("/multipleEquipments")
    public List<EquipmentDto> findEquipmentsByIds(@RequestBody List<Long> equipmentsId){
        return equipmentService.findEquipmentsByIds(equipmentsId);
    }
}
