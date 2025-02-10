package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.user.User;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartment;
import com.netceed.management.management_app.entity.user.userDepartment.UserDepartmentDto;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipment;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipmentDto;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipmentMapper;
import com.netceed.management.management_app.entity.equipment.StatusEquipment;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.repository.EquipmentRepository;
import com.netceed.management.management_app.repository.UserEquipmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEquipmentService{

    private final UserEquipmentRepository userEquipmentRepository;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;

    private final UserEquipmentMapper userEquipmentMapper;

    public List<UserEquipmentDto> getAll() {
        return userEquipmentRepository.findAll()
                .stream().map(userEquipmentMapper::toDto)
                .collect(Collectors.toList());
    }

    /***************************** Assign a existent equipment to a existent user *************************************/
    //Both of the objects have to exist before to create the assignment
    public UserEquipmentDto assignEquipmentToUser(Long userId, Long equipmentId){
            User user = userRepository.findById(userId).orElseThrow();
            Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow();

            Optional<UserEquipment> userEquipmentFound = userEquipmentRepository.findUserEquipmentByEquipmentId(equipmentId);

            UserEquipment assigment = new UserEquipment();

            if (userEquipmentFound.isEmpty()){
                assigment.setAssignedDateTime(LocalDateTime.now());
                assigment.setComments("Equipment " + equipment.getDescription() + " with the SN " + equipment.getSerialNumber());
                assigment.setEquipment(equipment);
                assigment.setUser(user);

                user.getUserEquipments().add(assigment);
                userRepository.save(user);

                equipment.setStatusEquipment(StatusEquipment.IN_USE);

                equipmentRepository.save(equipment);

                equipment.getUserEquipments().add(assigment);
                userEquipmentRepository.save(assigment);
            }else{
                throw new IllegalArgumentException("The equipment with the id " + equipmentId + " is already in use by other user");
            }

        return userEquipmentMapper.toDto(assigment);
    }

    //Assign multiple equipments object to a user object
    public List<UserEquipmentDto> assignEquipmentsToUser(Long userId, List<Long> equipmentsId) {
        Optional<User> userOpt = userRepository.findById(userId);
        List<UserEquipment> userEquipments = new ArrayList<>(); //List to store each object of User Equipment.

        if (userOpt.isPresent()){
            User user = userOpt.get();
            for (Long equipmentId : equipmentsId){ //For each given id on the List<Long>
                Optional<Equipment> equipmentOpt = equipmentRepository.findById(equipmentId);
                if (equipmentOpt.isPresent()){ //Checks if the given id it's present on the db
                    Equipment equipment = equipmentOpt.get();
                    equipment.setStatusEquipment(StatusEquipment.IN_USE);

                    UserEquipment userEquipment = new UserEquipment(); //object instanciate each assignment

                    userEquipment.setUser(user);
                    userEquipment.setEquipment(equipment);
                    userEquipment.setAssignedDateTime(LocalDateTime.now());
                    userEquipments.add(userEquipment);

                    equipmentRepository.save(equipment);
                }
            }
        }
        List<UserEquipment> assignmentsUser = userEquipmentRepository.saveAll(userEquipments);

        List<UserEquipmentDto> assignmentsUserDto = userEquipmentMapper.convertListUserEquipmentsToDto(assignmentsUser);

        return assignmentsUserDto; //save the collection that holds all the user equipment objects
    }

    public void returnEquipmentFromUser(Long userId, Long equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow();

        UserEquipment userEquipment = userEquipmentRepository.findUserEquipmentByEquipmentId(equipmentId).orElseThrow(); //this will allow to catch the id of the object by giving equipmentId and userId and delete

        boolean equipmentBelongsToUser = userEquipmentRepository.existsByEquipmentIdAndUserId(equipmentId, userId);

        if (!equipmentBelongsToUser) {//First check if the equipment_id given belongs to the user_id given
            throw new IllegalArgumentException("The id equipment id " + equipmentId + " doesn't belong to the user id " + userId);
        }

        equipment.setStatusEquipment(StatusEquipment.AVAILABLE); //changes the status of the object given
        equipmentRepository.save(equipment);
        userEquipmentRepository.deleteById(userEquipment.getId());

    }

    public List<UserEquipmentDto> getEquipmentsByUserId(Long userId) {
        return userEquipmentRepository.findByUserId(userId).stream().map(userEquipmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
