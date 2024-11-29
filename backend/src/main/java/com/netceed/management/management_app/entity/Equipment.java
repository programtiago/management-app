package com.netceed.management.management_app.entity;

import com.netceed.management.management_app.enums.StatusEquipment;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//Class represents a equipment that can be assigned to a Department or a User
//Ex: Keyboard
@Entity
@Data
@NoArgsConstructor
public class Equipment {

    private Long id;
    private String description;
    private String serialNumber;
    private String macAddress;
    private String brand;
    private String model;
    private String type; //SCANNER, SCREEN, MOUSE, DESKTOP
    private String location;
    private String function; //Packaging, Housing, Test, whatever...
    private String unity; //This will be department ??
    private User user;
    private LocalDateTime allocationDateTime;
    private LocalDateTime returningDateTime; //The equipment can be returned and assigned to another user for example
    private StatusEquipment statusEquipment = StatusEquipment.AVAILABLE;
    private String finalCondition; //The equipment returns and we make a intern validation to be assigned to other user

    public Equipment(String description, String serialNumber, String macAddress, String brand,
                     String model, String type, String location, String function,
                     String unity, LocalDateTime allocationDateTime,
                     LocalDateTime returningDateTime, StatusEquipment statusEquipment) {
        this.description = description;
        this.serialNumber = serialNumber;
        this.macAddress = macAddress;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.location = location;
        this.function = function;
        this.unity = unity;
        this.allocationDateTime = allocationDateTime;
        this.returningDateTime = returningDateTime;
        this.statusEquipment = statusEquipment;
    }
}
