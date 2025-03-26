package com.netceed.management.management_app.entity.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreationUserAssignEquipmentsRequestDto {

    @NotNull
    private UserDto user;
    private List<Long> equipmentIds;

}
