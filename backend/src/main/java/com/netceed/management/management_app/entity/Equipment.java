package com.netceed.management.management_app.entity;

import com.netceed.management.management_app.enums.StatusEquipment;
import jakarta.persistence.*;
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
    /*
    @JoinTable(
            name = "user_equipment",
            joinColumns = @JoinColumn(name = "equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
     */
    @OneToMany(mappedBy = "equipment")
    private Set<UserEquipment> userEquipments = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private StatusEquipment statusEquipment = StatusEquipment.AVAILABLE; //Depends. We can create a Equipment and not assign it to no one
    private String statusPhysic; //The equipment returns and we make a intern validation to be assigned to other user

    public Equipment(Long id, String description, String serialNumber, String macAddress, String brand,
                     String model, String type, String location, String goal,
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
        this.goal = goal;
        this.unity = unity;
        this.registryDate = registryDate;
        this.userEquipments = usersEquipments;
        this.statusEquipment = statusEquipment;
        this.statusPhysic = statusPhysic;
    }

    public void addUserEquipment(UserEquipment userEquipment){
        userEquipments.add(userEquipment);
        userEquipment.setEquipment(this);
    }
}
