package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.dto.EquipmentDto;
import com.netceed.management.management_app.entity.dto.UserDto;
import com.netceed.management.management_app.entity.mapper.EquipmentMapper;
import com.netceed.management.management_app.entity.mapper.UserMapper;
import com.netceed.management.management_app.enums.StatusEquipment;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.EquipmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import com.netceed.management.management_app.service.EquipmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public EquipmentDto createEquipmentForUser(Equipment equipment, Long userId) {
        return equipmentMapper.toDtoAssignToUser(equipmentRepository.save(equipment));
    }

    @Override
    public EquipmentDto create(Equipment equipment) {
        //To assign to a user
        return equipmentMapper.toDtoAssignToUser(equipmentRepository.save(equipment));
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
                   equipment.setStatusPhysic(equipmentDto.finalCondition());
                   equipment.setWorkstation(equipmentDto.function());
                   equipment.setMacAddress(equipmentDto.macAddress());
                   equipment.setLocation(equipmentDto.location());
                   equipment.setSerialNumber(equipmentDto.serialNumber());
                   equipment.setType(equipmentDto.type());
                   equipment.setUnity(equipmentDto.unity());
                   equipment.setUserEquipments(equipmentDto.usersEquipments());

                    return equipmentRepository.save(equipment);
                }).orElseThrow(() -> new ResourceNotFoundException("Operation failed because the resource with the id " + id + " doesn't exist."));
    }

    @Override
    public EquipmentDto getById(Long id) {
        return equipmentRepository.findById(id).map(equipmentMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Equipment resource not found with id " + id));
    }

    @Override
    public List<EquipmentDto> findEquipmentsByIds(List<Long> equipmentsId) {
        List<EquipmentDto> equipmentsFound = new ArrayList<>();

        for (Long equipmentId : equipmentsId) {
            Optional<Equipment> equipmentOptional = equipmentRepository.findById(equipmentId);

            if (equipmentOptional.isPresent()){
                Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow();
                equipmentsFound.add(equipmentMapper.toDto(equipment));
            }

        }
        return equipmentsFound;
    }

    @Override
    public List<EquipmentDto> getEquipmentsAvailable() {
       List<Equipment> equipments = equipmentRepository.findAllByStatusEquipment(StatusEquipment.AVAILABLE);

       return equipmentMapper.convertListEquipmentToDto(equipments);
    }
}
