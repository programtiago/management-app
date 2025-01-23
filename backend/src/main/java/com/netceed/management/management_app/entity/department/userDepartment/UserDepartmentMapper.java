package com.netceed.management.management_app.entity.department.userDepartment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDepartmentMapper{
    public UserDepartmentDto toDto(UserDepartment userDepartment){
        if (userDepartment == null){
            return null;
        }

        return new UserDepartmentDto(userDepartment.getId(), userDepartment.getUser(), userDepartment.getDepartment(),
                userDepartment.getAssignedDate(),userDepartment.getComments());
    }

    public UserDepartment toEntity(UserDepartmentDto userDepartmentDto){
        UserDepartment userDepartment = new UserDepartment(userDepartmentDto.id(), userDepartmentDto.user(), userDepartmentDto.department(), userDepartmentDto.assignedDate(), userDepartmentDto.comments());

        if (userDepartmentDto.id() != null) {
            userDepartment.setId(userDepartmentDto.id());
        }
        return userDepartment;
    }

    public List<UserDepartmentDto> convertListDepartmentToListDepartmentDto(List<UserDepartment> userDepartments){
        List<UserDepartmentDto> userDepartmentsDtos = new ArrayList<>();
        for (UserDepartment userDepartment : userDepartments){
            UserDepartmentDto userEquipmentDto = new UserDepartmentDto(userDepartment.getId(), userDepartment.getUser(), userDepartment.getDepartment(), userDepartment.getAssignedDate(), userDepartment.getComments());
            userDepartmentsDtos.add(userEquipmentDto);
        }
        return userDepartmentsDtos;
    }
}
