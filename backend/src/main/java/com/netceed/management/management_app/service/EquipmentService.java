package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.userEquipment.UserEquipment;
import com.netceed.management.management_app.entity.equipment.EquipmentDto;
import com.netceed.management.management_app.entity.user.UserDto;
import com.netceed.management.management_app.entity.equipment.EquipmentMapper;
import com.netceed.management.management_app.entity.user.UserMapper;
import com.netceed.management.management_app.entity.equipment.StatusEquipment;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    private final EquipmentMapper equipmentMapper;
    private final UserMapper userMapper;

    private final UserService userService;
    private final UserEquipmentService userEquipmentService;

    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }
    public EquipmentDto createEquipmentForUser(EquipmentDto newEquipment, Long userId) throws NoSuchFieldException {
        UserEquipment userEquipment = new UserEquipment();
        UserDto userFound = userService.getById(userId);

        boolean serialNumberAlreadyRegistered = serialNumberExists(newEquipment.serialNumber());

        if (serialNumberAlreadyRegistered) {
            throw new NoSuchFieldException("Serial Number " +  newEquipment.serialNumber() + " already belong to a equipment");
        }

        if (userFound.id() != null){
            newEquipment.setStatusEquipment(StatusEquipment.IN_USE);
            userEquipment.setEquipment(equipmentMapper.toEntity(newEquipment));
            userEquipment.setAssignedDate(LocalDateTime.now());
            userEquipment.setUser(userMapper.toEntity(userFound));
            userEquipment.setComments(newEquipment.description() + " was assigned at " + userEquipment.getAssignedDate());
        }

        create(newEquipment);

        userEquipmentService.assignEquipmentToUser(userId, newEquipment.id());

        return equipmentMapper.toEntityAssignToUser(newEquipment);
    }

    //Create simple equipment object without assigning to anyone or anywhere
    public EquipmentDto create(EquipmentDto equipment) {
        Equipment newEquipment = equipmentMapper.toEntity(equipment);

        return equipmentMapper.toDto(equipmentRepository.save(newEquipment));
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
