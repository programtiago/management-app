package com.netceed.management.management_app.entity.user.userEquipment;

import com.netceed.management.management_app.entity.trackaudit.TrackAuditService;
import com.netceed.management.management_app.entity.user.CreationUserAssignEquipmentsRequestDto;
import com.netceed.management.management_app.entity.user.User;
import com.netceed.management.management_app.entity.user.UserDto;
import com.netceed.management.management_app.service.UserEquipmentService;
import com.netceed.management.management_app.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-equipments")
@RequiredArgsConstructor
public class UserEquipmentController {

    private final UserEquipmentService userEquipmentService;
    private final TrackAuditService trackAuditService;
    private final UserService userService;

    @GetMapping
    public List<UserEquipmentDto> getAllAssignments(){
        return userEquipmentService.getAll();
    }

    @GetMapping("/{userId}/equipments")
    public List<UserEquipmentDto> getEquipmentsByUserId(@PathVariable Long userId){
        return userEquipmentService.getEquipmentsByUserId(userId);
    }

    @PostMapping("/create-with-equipments")
    @Transactional
    public List<UserEquipmentDto> createUserWithEquipmentsAssignment(@RequestBody CreationUserAssignEquipmentsRequestDto request){
        UserDto createdUser = userService.create(request.getUser());

        if (createdUser.id() == null){
           throw new RuntimeException("User ID is null after creation");
        }

        if (request.getEquipmentIds() == null || request.getEquipmentIds().isEmpty()){
            return List.of();
        }

        List<UserEquipmentDto> assignments = userEquipmentService.assignEquipmentsToUser(
                createdUser.id(),
                request.getEquipmentIds()
        );

        assignments.forEach(assignment -> {
            trackAuditService.logAction(
                    assignment.id(),
                    String.format("Assigned equipment with serial %s to new user [%d] - %s %s",
                            assignment.equipment().getSerialNumber(),
                            assignment.user().getWorkNumber(),
                            assignment.user().getFirstName(),
                            assignment.user().getLastName()
                    ),
                    "testusername",
                    "UserEquipment"
            );
        });

        return assignments;
    }

    /*
    @GetMapping("/{equipmentId}/user")
    public ResponseEntity<UserDto> getUserByEquipmentId(@PathVariable Long equipmentId){
        return ResponseEntity.ok(userEquipmentService.getUserByEquipmentId(equipmentId).orElseThrow());
    }
     */

    @PostMapping("/{userId}/equipment/{equipmentId}")
    public UserEquipmentDto assignEquipmentToUser(@PathVariable @Param("userId") Long userId, @PathVariable @Param("equipmentId") Long equipmentId) throws BadRequestException {
        UserEquipmentDto assignmentUserToEquipment = userEquipmentService.assignEquipmentToUser(userId, equipmentId);

        if (assignmentUserToEquipment != null){
            trackAuditService.logAction(assignmentUserToEquipment.id(), "Assigned equipment with the serial  " + assignmentUserToEquipment.equipment().getSerialNumber() + " to user [ " + assignmentUserToEquipment.user().getWorkNumber() + " ] - " +
                    assignmentUserToEquipment.user().getFirstName() + " " + assignmentUserToEquipment.user().getLastName(), "testusername", "UserEquipment");
        }

        return assignmentUserToEquipment;

    }

    @PostMapping("/{userId}/equipments")
    public List<UserEquipmentDto> assignMultipleEquipmentsToUser(@PathVariable @Param("userId") Long userId, @RequestBody List<Long> equipmentsId){
        List<UserEquipmentDto> userEquipmentAssignmentsList = userEquipmentService.assignEquipmentsToUser(userId, equipmentsId);

        for (UserEquipmentDto userEquipmentDto : userEquipmentAssignmentsList) {
            if (!userEquipmentAssignmentsList.isEmpty()){
                trackAuditService.logAction(userEquipmentDto.id(), "Assigned equipment with the serial  " + userEquipmentDto.equipment().getSerialNumber() + " to user [ " + userEquipmentDto.user().getWorkNumber() + " ] - " +
                        userEquipmentDto.user().getFirstName() + " " + userEquipmentDto.user().getLastName(), "testusername", "UserEquipment");
            }
        }

        return userEquipmentAssignmentsList;
    }

    @DeleteMapping("/{userId}/equipment/{equipmentId}")
    public void returnEquipmentFromUser(@PathVariable @Param("userId") Long userId, @PathVariable @Param("equipmentId") Long equipmentId){
        userEquipmentService.returnEquipmentFromUser(userId, equipmentId);
    }
}
