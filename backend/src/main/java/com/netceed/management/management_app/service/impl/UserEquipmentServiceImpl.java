package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.mapper.EquipmentMapper;
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
    private final EquipmentMapper equipmentMapper;

    @Override
    public List<UserEquipment> getAll() {
        return userEquipmentRepository.findAll();
    }

    @Override
    public void assignUserToEquipment(Long userId, Long equipmentId) {
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
    }

    @Override
    public boolean doesEquipmentBelongToUser(Long equipmentId, Long userId) {
        return userEquipmentRepository.existsByEquipmentIdAndUserId(equipmentId, userId);
    }

    @Override
    public void returnEquipmentFromUser(Long userId, Long equipmentId) {
        User user = userRepository.findById(userId).orElseThrow();
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow();

        UserEquipment userEquipment = userEquipmentRepository.findUserEquipmentByEquipmentId(equipmentId).orElseThrow();

        boolean equipmentBelongsToUser = userEquipmentRepository.existsByEquipmentIdAndUserId(equipmentId, userId);

        if (!equipmentBelongsToUser) {//First check if the equipment_id given belongs to the user_id given
            throw new IllegalArgumentException("The id equipment id " + equipmentId + " doesn't belong to the user id " + userId);
        }

        equipment.setStatusEquipment(StatusEquipment.AVAILABLE);
        equipmentRepository.save(equipment);
        userEquipmentRepository.deleteById(userEquipment.getId());

    }
}
