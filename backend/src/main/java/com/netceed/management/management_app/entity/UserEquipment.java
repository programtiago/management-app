package com.netceed.management.management_app.entity;

import com.fasterxml.jackson.annotation.*;
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
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    //@JsonManagedReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Column(nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime assignedDate;
    private LocalDateTime returnDate;

    private String comments;
}
