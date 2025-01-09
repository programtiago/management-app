package com.netceed.management.management_app.entity.user;

import com.netceed.management.management_app.entity.equipment.EquipmentDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserDto toDto(User user){
        if (user == null){
            return null;
        }

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getWorkNumber(), user.getBirthdayDate(), user.getDepartment(), user.getWorkStatus(), user.getShift(),
                user.getRecruitmentCompany(), user.getRegistryDate(), user.getAdmissionDate(), user.isActive(), user.getUserRole(), user.getEmail(), user.getNif(), user.getContactNumber(), user.getPassword(),
                user.isAvailableForVacation(), user.getUpdatedAt(), user.getUserEquipments());
    }

    public User toEntity(UserDto userDto){
        User user = new User();

        if (userDto.id() != null){
            user.setId(userDto.id());

        }

        user = new User(userDto.id(), userDto.firstName(), userDto.lastName(), userDto.nif(), userDto.workNumber(), userDto.birthdayDate(), userDto.department(), userDto.workStatus(), userDto.shift(),
                userDto.recruitmentCompany(), userDto.registryDate(), userDto.admissionDate(), userDto.isActive(), userDto.userRole(),  userDto.email(), userDto.contactNumber(), userDto.password(),
                userDto.updatedAt());

        return user;
    }

    public List<UserDto> convertListUserToDto(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users){
            UserDto userDto = new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getWorkNumber(), user.getBirthdayDate(),
                    user.getDepartment(), user.getWorkStatus(), user.getShift(), user.getRecruitmentCompany(), user.getRegistryDate(),
                    user.getAdmissionDate(), user.isActive(), user.getUserRole(), user.getEmail(), user.getNif(), user.getContactNumber(), user.getPassword(), user.isAvailableForVacation(), user.getUpdatedAt(), user.getUserEquipments());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public UserDto fromDtoToDto(UserDto userDto){
        if (userDto == null){
            return null;
        }

        return new UserDto(userDto.id(), userDto.firstName(), userDto.lastName(), userDto.workNumber(), userDto.birthdayDate(),
                userDto.department(), userDto.workStatus(), userDto.shift(), userDto.recruitmentCompany(), userDto.registryDate(),
                userDto.admissionDate(), userDto.isActive(), userDto.userRole(), userDto.email(), userDto.nif(), userDto.contactNumber(), userDto.password(), userDto.isAvailableForVacation(), userDto.updatedAt(), userDto.userEquipments());
    }
}
