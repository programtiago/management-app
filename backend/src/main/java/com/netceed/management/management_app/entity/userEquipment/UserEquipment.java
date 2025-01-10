package com.netceed.management.management_app.entity.userEquipment;

import com.fasterxml.jackson.annotation.*;
import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Column(nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy'T'HH:mm:ss")
    private String assignedDate;
    @JsonFormat(pattern="dd-MM-yyyy'T'HH:mm:ss")
    private String returnDate;

    private String comments;

}
