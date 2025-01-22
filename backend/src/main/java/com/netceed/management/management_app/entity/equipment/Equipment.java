package com.netceed.management.management_app.entity.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netceed.management.management_app.entity.userEquipment.UserEquipment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

//Class represents a equipment that can be assigned to a Department or a User
//Ex: Keyboard
@Entity
@Data
@NoArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String description;
    @NotNull
    private String serialNumber;
    @NotNull
    private String brand;
    @NotNull
    private String model;
    private String type; //SCANNER, SCREEN, MOUSE, DESKTOP
    private String location; //PROD -> Packaging  Department
    private String workstation; //There are multiple workstation inside a location
    private String unity; //Packaging, Housing, Test, whatever...
    @NotNull
    private String registryDate;
    @OneToMany(mappedBy = "equipment")
    @JsonIgnore
    private Set<UserEquipment> userEquipments;
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    private StatusEquipment statusEquipment = StatusEquipment.AVAILABLE; //Depends. We can create a Equipment and not assign it to no one
    private String statusPhysic; //The equipment returns and we make a intern validation to be assigned to other user

    public Equipment(Long id, String description, String serialNumber, String brand,
                     String model, String type, String location, String workstation,
                     String unity, boolean isActive, String statusPhysic) {
        this.id = id;
        this.description = description;
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.location = location;
        this.workstation = workstation;
        this.unity = unity;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.userEquipments = new HashSet<>();
        this.isActive = isActive;
        this.statusEquipment = StatusEquipment.AVAILABLE;
        this.statusPhysic = statusPhysic;
    }

    //For   -> type equipment assignment - NONE
    public Equipment(String description, String serialNumber, String brand, String model, String category, String workstation){
        this.description = description;
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.model = model;
        this.type = category;
        this.workstation = workstation;
    }
}
