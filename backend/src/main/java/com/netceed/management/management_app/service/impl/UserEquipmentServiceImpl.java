package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.entity.dto.EquipmentDto;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.mapper.EquipmentMapper;
import com.netceed.management.management_app.entity.mapper.UserMapper;
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
            EquipmentDto equipmentDto = equipmentMapper.toDto(equipmentRepository.findById(equipmentId).orElseThrow());

            UserEquipment userEquipment = new UserEquipment();

            Optional<UserEquipment> userEquipmentFound = userEquipmentRepository.findUserEquipmentByEquipmentId(equipmentId);

            if (userEquipmentFound.isEmpty()){
                userEquipment.setAssignedDate(LocalDateTime.now());
                userEquipment.setComments("Equipment " + equipmentDto.description() + " with the SN " + equipmentDto.serialNumber() + " assigned at " + userEquipment.getAssignedDate());
                userEquipment.setEquipment(equipmentMapper.toEntity(equipmentDto));
                userEquipment.setUser(userMapper.toEntity(userDto));

                userEquipmentRepository.save(userEquipment);

                equipmentDto.usersEquipments().add(userEquipment);
                userDto.userEquipments().add(userEquipment);

                userRepository.save(userMapper.toEntity(userDto));
                equipmentRepository.save(equipmentMapper.toEntity(equipmentDto));
            }

            if (userEquipmentFound.isPresent()){
                throw new IllegalArgumentException("The equipment with the id " + equipmentId + " is already in use by other user");
            }
    }
}
