package com.netceed.management.management_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.netceed.management.management_app.enums.StatusEquipment;
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
    private String macAddress;
    @NotNull
    private String brand;
    @NotNull
    private String model;
    private String type; //SCANNER, SCREEN, MOUSE, DESKTOP
    private String location; //PROD -> Packaging  Department
    private String workstation; //There are multiple workstation inside a location
    private String unity; //Packaging, Housing, Test, whatever...
    @NotNull
    private String registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    /*
    @JoinTable(
            name = "user_equipment",
            joinColumns = @JoinColumn(name = "equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
     */
    @OneToMany(mappedBy = "equipment")
    @JsonBackReference
    private Set<UserEquipment> userEquipments = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private StatusEquipment statusEquipment = StatusEquipment.AVAILABLE; //Depends. We can create a Equipment and not assign it to no one
    private String statusPhysic; //The equipment returns and we make a intern validation to be assigned to other user

    public Equipment(Long id, String description, String serialNumber, String macAddress, String brand,
                     String model, String type, String location, String workstation,
                     String unity, String registryDate, Set<UserEquipment> usersEquipments,
                     StatusEquipment statusEquipment, String statusPhysic) {
        this.id = id;
        this.description = description;
        this.serialNumber = serialNumber;
        this.macAddress = macAddress;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.location = location;
        this.workstation = workstation;
        this.unity = unity;
        this.registryDate = registryDate;
        this.userEquipments = usersEquipments;
        this.statusEquipment = statusEquipment;
        this.statusPhysic = statusPhysic;
    }

    //For   -> type equipment assignment - NONE
    public Equipment(Long id, String description, String serialNumber, String brand, String model, String category, String workstation){
        this.id = id;
        this.description = description;
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.model = model;
        this.type = category;
        this.workstation = workstation;
    }

    //Assign equipment to user
    public void addUserEquipment(UserEquipment userEquipment){
        userEquipments.add(userEquipment);
        userEquipment.setEquipment(this);
    }
}
