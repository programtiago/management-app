package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.dto.UserEquipmentDto;
import com.netceed.management.management_app.entity.mapper.EquipmentMapper;
import com.netceed.management.management_app.entity.mapper.UserEquipmentMapper;
import com.netceed.management.management_app.entity.mapper.UserMapper;
import com.netceed.management.management_app.enums.StatusEquipment;
import com.netceed.management.management_app.repository.EquipmentRepository;
import com.netceed.management.management_app.repository.UserEquipmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.UserEquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserEquipmentServiceImpl implements UserEquipmentService {

    private final UserEquipmentRepository userEquipmentRepository;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserMapper userMapper;
    private final UserEquipmentMapper userEquipmentMapper;
    private final EquipmentMapper equipmentMapper;

    @Override
    public List<UserEquipment> getAll() {
        return userEquipmentRepository.findAll();
    }

    @Override
    public UserEquipment assignUserToEquipment(Long userId, Long equipmentId) {
            UserDto userDto = userMapper.toDto(userRepository.findById(userId).orElseThrow());
            Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow();

            UserEquipment userEquipment = new UserEquipment();

            Optional<UserEquipment> userEquipmentFound = userEquipmentRepository.findUserEquipmentByEquipmentId(equipmentId);
            //UserEquipmentDto userEquipmentDto = null;

        if (userEquipmentFound.isEmpty()){
                userEquipment.setAssignedDate(LocalDateTime.now());
                userEquipment.setComments("Equipment " + equipment.getDescription() + " with the SN " + equipment.getSerialNumber() + " assigned at " + userEquipment.getAssignedDate());
                userEquipment.setEquipment(equipment);
                userEquipment.setUser(userMapper.toEntity(userDto));
                //userEquipmentDto = userEquipmentMapper.toDto(userEquipment);
                System.out.println(userEquipment);

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

    @Override
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
}
