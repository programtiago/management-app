package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.dto.EquipmentDto;
import com.netceed.management.management_app.entity.mapper.EquipmentMapper;
import com.netceed.management.management_app.enums.StatusEquipment;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;

    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }
    public Equipment createEquipmentForUser(EquipmentDto equipment, Long userId) {
        return equipmentMapper.toEntityAssignToUser(equipment);
    }

    //Create simple equipment object without assigning to anyone or anywhere
    public Equipment create(EquipmentDto equipment) {
        Equipment newEquipment = equipmentMapper.toEntity(equipment);

        return equipmentRepository.save(newEquipment);
    }

    public List<EquipmentDto> getAllEquipments() {
        return equipmentRepository.findAll()
                .stream().map(equipmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean serialNumberExists(String serialNumber) {
        return equipmentRepository.findBySerialNumber(serialNumber).isPresent();
    }

    public Equipment update(EquipmentDto equipmentDto, Long id) {
        return equipmentRepository.findById(id)
                .map(equipment -> {
                   equipment.setBrand(equipmentDto.brand());
                   equipment.setModel(equipmentDto.model());
                   equipment.setStatusEquipment(equipmentDto.statusEquipment());
                   equipment.setDescription(equipmentDto.description());
                   equipment.setStatusPhysic(equipmentDto.finalCondition());
                   equipment.setWorkstation(equipmentDto.workstation());
                   equipment.setMacAddress(equipmentDto.macAddress());
                   equipment.setLocation(equipmentDto.location());
                   equipment.setSerialNumber(equipmentDto.serialNumber());
                   equipment.setType(equipmentDto.type());
                   equipment.setUnity(equipmentDto.unity());
                   equipment.setUserEquipments(equipmentDto.usersEquipments());

                    return equipmentRepository.save(equipment);
                }).orElseThrow(() -> new ResourceNotFoundException("Operation failed because the resource with the id " + id + " doesn't exist."));
    }

    public EquipmentDto getById(Long id) {
        return equipmentRepository.findById(id).map(equipmentMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Equipment resource not found with id " + id));
    }

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

    public List<EquipmentDto> getEquipmentsAvailable() {
       List<Equipment> equipments = equipmentRepository.findAllByStatusEquipment(StatusEquipment.AVAILABLE);

       return equipmentMapper.convertListEquipmentToDto(equipments);
    }
}
