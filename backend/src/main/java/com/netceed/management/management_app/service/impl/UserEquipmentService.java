package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.dto.UserEquipmentDto;
import com.netceed.management.management_app.entity.mapper.UserEquipmentMapper;
import com.netceed.management.management_app.entity.mapper.UserMapper;
import com.netceed.management.management_app.enums.StatusEquipment;
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
    private final UserEquipmentMapper userEquipmentMapper;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserMapper userMapper;

    public List<UserEquipmentDto> getAll() {
        return userEquipmentRepository.findAll()
                .stream().map(userEquipmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserEquipment assignEquipmentToUser(Long userId, Long equipmentId) {
            UserDto userDto = userMapper.toDto(userRepository.findById(userId).orElseThrow());
            Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow();

            UserEquipment userEquipment = new UserEquipment();

            Optional<UserEquipment> userEquipmentFound = userEquipmentRepository.findUserEquipmentByEquipmentId(equipmentId);

        if (userEquipmentFound.isEmpty()){
                userEquipment.setAssignedDate(LocalDateTime.now());
                userEquipment.setComments("Equipment " + equipment.getDescription() + " with the SN " + equipment.getSerialNumber() + " assigned at " + userEquipment.getAssignedDate());
                userEquipment.setEquipment(equipment);
                userEquipment.setUser(userMapper.toEntity(userDto));

                userEquipmentRepository.save(userEquipment);
                equipment.getUserEquipments().add(userEquipment);

                userDto.userEquipments().add(userEquipment);
                userRepository.save(userMapper.toEntity(userDto));

                equipment.setStatusEquipment(StatusEquipment.IN_USE);
                equipmentRepository.save(equipment);
            }

            if (userEquipmentFound.isPresent()){
                throw new IllegalArgumentException("The equipment with the id " + equipmentId + " is already in use by other user");
            }

            return userEquipment;
    }

    //Assign multiple equipments object to a user object
    public List<UserEquipment> assignEquipmentsToUser(Long userId, List<Long> equipmentsId) {
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
                    userEquipment.setAssignedDate(LocalDateTime.now());
                    userEquipments.add(userEquipment);

                    equipmentRepository.save(equipment);
                }
            }

        }

        return userEquipmentRepository.saveAll(userEquipments); //save the collectiont that holds all the user equipment objects
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

    /*
    @Override
    public Optional<UserDto> getUserByEquipmentId(Long equipmentId) {
        return userEquipmentRepository.findUserByEquipmentId(equipmentId);
    }

     */
}
