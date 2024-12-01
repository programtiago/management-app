package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.dto.EquipmentDto;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.mapper.EquipmentMapper;
import com.netceed.management.management_app.entity.mapper.UserMapper;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.EquipmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.EquipmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EquipmentMapper equipmentMapper;

    @Override
    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }

    @Override
    public EquipmentDto create(Equipment equipment) {
        return equipmentMapper.toDto(equipmentRepository.save(equipment));
    }

    @Override
    public List<EquipmentDto> getAllEquipments() {
        return equipmentRepository.findAll()
                .stream().map(equipmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean serialNumberExists(String serialNumber) {
        return equipmentRepository.findBySerialNumber(serialNumber).isPresent();
    }

    @Override
    public Equipment update(EquipmentDto equipmentDto, Long id) {
        return equipmentRepository.findById(id)
                .map(equipment -> {
                   equipment.setBrand(equipmentDto.brand());
                   equipment.setModel(equipmentDto.model());
                   equipment.setStatusEquipment(equipmentDto.statusEquipment());
                   equipment.setDescription(equipmentDto.description());
                   equipment.setAllocationDateTime(equipmentDto.allocationDateTime());
                   equipment.setStatusPhysic(equipmentDto.finalCondition());
                   equipment.setGoal(equipmentDto.function());
                   equipment.setMacAddress(equipmentDto.macAddress());
                   equipment.setReturningDateTime(equipmentDto.returningDateTime());
                   equipment.setLocation(equipmentDto.location());
                   equipment.setSerialNumber(equipmentDto.serialNumber());
                   equipment.setType(equipmentDto.type());
                   equipment.setUnity(equipmentDto.unity());
                   equipment.setUsers(equipmentDto.users());

                    return equipmentRepository.save(equipment);
                }).orElseThrow(() -> new ResourceNotFoundException("Operation failed because the resource with the id " + id + " doesn't exist."));
    }

    @Override
    public EquipmentDto getById(Long id) {
        return equipmentRepository.findById(id).map(equipmentMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Equipment resource not found with id " + id));
    }
}
