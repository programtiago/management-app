package com.netceed.management.management_app.entity;

import com.netceed.management.management_app.enums.StatusEquipment;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//Class represents a equipment that can be assigned to a Department or a User
//Ex: Keyboard
@Entity
@Data
@NoArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String serialNumber;
    private String macAddress;
    private String brand;
    private String model;
    private String type; //SCANNER, SCREEN, MOUSE, DESKTOP
    private String location;
    private String goal; //Packaging, Housing, Test, whatever...
    private String unity; //This will be department ??
    private String registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    @ManyToMany(mappedBy = "equipments")
    private List<User> users;
    private LocalDateTime allocationDateTime;
    private LocalDateTime returningDateTime; //The equipment can be returned and assigned to another user for example
    private StatusEquipment statusEquipment = StatusEquipment.AVAILABLE;
    private String statusPhysic; //The equipment returns and we make a intern validation to be assigned to other user

    public Equipment(Long id, String description, String serialNumber, String macAddress, String brand,
                     String model, String type, String location, String goal,
                     String unity, String registryDate, List<User> users, LocalDateTime allocationDateTime,
                     LocalDateTime returningDateTime, StatusEquipment statusEquipment, String statusPhysic) {
        this.id = id;
        this.description = description;
        this.serialNumber = serialNumber;
        this.macAddress = macAddress;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.location = location;
        this.goal = goal;
        this.unity = unity;
        this.registryDate = registryDate;
        this.users = users;
        this.allocationDateTime = allocationDateTime;
        this.returningDateTime = returningDateTime;
        this.statusEquipment = statusEquipment;
        this.statusPhysic = statusPhysic;
    }
}
