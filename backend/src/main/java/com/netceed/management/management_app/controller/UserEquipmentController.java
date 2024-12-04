package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.entity.dto.UserEquipmentDto;
import com.netceed.management.management_app.service.impl.UserEquipmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user-equipments")
@RequiredArgsConstructor
public class UserEquipmentController {

    private final UserEquipmentServiceImpl userEquipmentService;

    @GetMapping("/all")
    public ResponseEntity<List<UserEquipment>> getAllAssignments(){
        return ResponseEntity.ok(userEquipmentService.getAll());
    }

    @PostMapping("/{userId}/equipment/{equipmentId}")
    public ResponseEntity<UserEquipment> assignEquipmentToUser(@PathVariable @Param("userId") Long userId, @PathVariable @Param("equipmentId") Long equipmentId){
        return ResponseEntity.ok(userEquipmentService.assignEquipmentToUser(userId, equipmentId));
    }

    @PostMapping("/{userId}/equipments")
    public ResponseEntity<List<UserEquipment>> assignMultipleEquipmentsToUser(@PathVariable @Param("userId") Long userId, @RequestBody List<Long> equipmentsId){
        List<UserEquipment> userEquipments = userEquipmentService.assignEquipmentsToUser(userId, equipmentsId);
        return ResponseEntity.ok(userEquipments);
    }

    @DeleteMapping("/{userId}/equipment/{equipmentId}")
    public void returnEquipmentFromUser(@PathVariable @Param("userId") Long userId, @PathVariable @Param("equipmentId") Long equipmentId){
        userEquipmentService.returnEquipmentFromUser(userId, equipmentId);
    }
}
