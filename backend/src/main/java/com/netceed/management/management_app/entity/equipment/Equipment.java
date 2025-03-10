package com.netceed.management.management_app.entity.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netceed.management.management_app.entity.equipment.converters.StatusEquipmentConverter;
import com.netceed.management.management_app.entity.user.userEquipment.UserEquipment;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
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
@Table(name = "TB_EQUIPMENT")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String description;
    @NotNull
    @Column(name = "serial_number", unique = true, length = 20)
    private String serialNumber;
    @NotNull
    @Column(name = "brand", nullable = false, length = 20)
    private String brand;
    @NotNull
    @Column(name = "model", nullable = false, length = 20)
    private String model;
    @Column(name = "type", nullable = false, length = 10)
    private String type; //SCANNER, SCREEN, MOUSE, DESKTOP
    @Column(name = "location", nullable = false, length = 20)
    private String location; //PROD -> Packaging  Department
    @Column(name = "workstation", nullable = false, length = 20)
    private String workstation; //There are multiple workstation inside a location
    @Column(name = "unity", nullable = false, length = 20)
    private String unity; //Packaging, Housing, Test, whatever...
    @NotNull
    @Column(name = "registry_date", nullable = false, length = 20)
    private String registryDate;
    @OneToMany(mappedBy = "equipment")
    @JsonIgnore
    private Set<UserEquipment> userEquipments;
    @Column(name = "is_active", nullable = false, length = 20)
    private boolean isActive;
    @Convert(converter = StatusEquipmentConverter.class)
    @Column(name = "status_equipment", nullable = false, length = 20)
    private StatusEquipment statusEquipment = StatusEquipment.AVAILABLE; //Depends. We can create a Equipment and not assign it to no one
    @Column(name = "status_physic", nullable = false, length = 20)
    private String statusPhysic; //The equipment returns and we make a intern validation to be assigned to other user

    public Equipment(Long id, String description, String serialNumber, String brand,
                     String model, String type, String location, String workstation,
                     String unity, boolean isActive, StatusEquipment statusEquipment, String statusPhysic) {
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
        this.statusEquipment = statusEquipment;
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
