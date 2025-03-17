package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.equipment.*;
import com.netceed.management.management_app.entity.trackaudit.TrackAuditService;
import com.netceed.management.management_app.entity.user.User;
import com.netceed.management.management_app.entity.user.UserDto;
import com.netceed.management.management_app.entity.user.UserMapper;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipmentDto;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.EquipmentRepository;
import com.netceed.management.management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    private final EquipmentMapper equipmentMapper;
    private final UserMapper userMapper;

    private final UserEquipmentService userEquipmentService;
    private final TrackAuditService trackAuditService;

    //The resource is not deleted from the repo. The flag isActive controls if the equipment can be shown as available
    public void delete(Long id) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow();

        if (equipment.getId() != null){
            equipment.setStatusEquipment(StatusEquipment.NOT_AVAILABLE);
            equipment.setActive(false);
        }

        equipmentRepository.save(equipment);
    }
    public UserEquipmentDto createEquipmentForUser(EquipmentDto newEquipment, Long userId) throws IllegalArgumentException{
        User user = userRepository.findById(userId).orElseThrow();
        UserDto userFound = userMapper.toDto(user);

        boolean serialNumberAlreadyRegistered = serialNumberExists(newEquipment.serialNumber());

        if (serialNumberAlreadyRegistered) {
            throw new IllegalArgumentException("Serial Number " +  newEquipment.serialNumber() + " already belong to a equipment");
        }

        Equipment savedEquipment = equipmentRepository.save(equipmentMapper.toEntity(newEquipment));

        if (userFound != null){
            equipmentRepository.save(savedEquipment);
        }

        return userEquipmentService.assignEquipmentToUser(userId, savedEquipment.getId());
    }

    //Create simple equipment object without assigning to anyone or anywhere
    public EquipmentDto create(EquipmentDto equipment) {
        Equipment equipmentToSave = equipmentRepository.save(equipmentMapper.toEntity(equipment));

        if (equipmentToSave.getId() != null){
            trackAuditService.logAction(equipmentToSave.getId(), "Created equipment with serial " + equipmentToSave.getSerialNumber(), "testUsername", "Equipment");
        }

        return equipmentMapper.toDto(equipmentMapper.toEntity(equipment));
    }

    public EquipmentPageDto getAllEquipments(int page, int pageSize) {
        Page<Equipment> pageEquipment = equipmentRepository.findAll(PageRequest.of(page, pageSize));
        List<EquipmentDto> equipments = pageEquipment.get().map(equipmentMapper::toDto).toList();

        return new EquipmentPageDto(equipments, pageEquipment.getTotalElements(), pageEquipment.getTotalPages());
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
                   equipment.setLocation(equipmentDto.location());
                   equipment.setSerialNumber(equipmentDto.serialNumber());
                   equipment.setType(equipmentDto.type());
                   equipment.setUnity(equipmentDto.unity());
                   equipment.setActive(equipment.isActive());
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

    public List<EquipmentDto> search(String query){
        return equipmentMapper.convertListEquipmentToDto(equipmentRepository.findBKeywordSn(query));
    }

    public List<EquipmentDto> searchEquipmentsStartsWithDescription(String description){
        return equipmentMapper.convertListEquipmentToDto(equipmentRepository.findEquipmentsStartsWithDescription(description));
    }

    public List<EquipmentDto> searchEquipmentsContainsDescription(String description){
        return equipmentMapper.convertListEquipmentToDto(equipmentRepository.findEquipmentsContainsDescription(description));
    }

    public List<EquipmentDto> searchEquipmentsStartsWithSerialNumber(String serialNumber){
        return equipmentMapper.convertListEquipmentToDto(equipmentRepository.findEquipmentsStartsWithSerialNumber(serialNumber));
    }

    public List<EquipmentDto> searchEquipmentsContainsSerialNumber(String serialNumber){
        return equipmentMapper.convertListEquipmentToDto(equipmentRepository.findEquipmentsContainsSerialNumber(serialNumber));
    }

    public List<EquipmentDto> searchEquipmentsByType(String type){
        return equipmentMapper.convertListEquipmentToDto(equipmentRepository.findEquipmentsByType(type));
    }
}
