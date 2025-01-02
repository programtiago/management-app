package com.netceed.management.management_app.entity.userEquipment;

import com.netceed.management.management_app.service.UserEquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-equipments")
@RequiredArgsConstructor
public class UserEquipmentController {

    private final UserEquipmentService userEquipmentService;

    @GetMapping
    public List<UserEquipmentDto> getAllAssignments(){
        return userEquipmentService.getAll();
    }

    @GetMapping("/{userId}/equipments")
    public List<UserEquipmentDto> getEquipmentsByUserId(@PathVariable Long userId){
        return userEquipmentService.getEquipmentsByUserId(userId);
    }

    /*
    @GetMapping("/{equipmentId}/user")
    public ResponseEntity<UserDto> getUserByEquipmentId(@PathVariable Long equipmentId){
        return ResponseEntity.ok(userEquipmentService.getUserByEquipmentId(equipmentId).orElseThrow());
    }
     */

    @PostMapping("/{userId}/equipment/{equipmentId}")
    public UserEquipmentDto assignEquipmentToUser(@PathVariable @Param("userId") Long userId, @PathVariable @Param("equipmentId") Long equipmentId){
        return userEquipmentService.assignEquipmentToUser(userId, equipmentId);
    }

    @PostMapping("/{userId}/equipments")
    public List<UserEquipmentDto> assignMultipleEquipmentsToUser(@PathVariable @Param("userId") Long userId, @RequestBody List<Long> equipmentsId){
        return userEquipmentService.assignEquipmentsToUser(userId, equipmentsId);
    }

    @DeleteMapping("/{userId}/equipment/{equipmentId}")
    public void returnEquipmentFromUser(@PathVariable @Param("userId") Long userId, @PathVariable @Param("equipmentId") Long equipmentId){
        userEquipmentService.returnEquipmentFromUser(userId, equipmentId);
    }
}
