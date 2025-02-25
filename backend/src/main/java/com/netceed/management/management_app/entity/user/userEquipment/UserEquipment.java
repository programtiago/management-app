package com.netceed.management.management_app.entity.user.userEquipment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_USER_EQUIPMENT")
public class UserEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Column(name = "assigned_date_time", nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime assignedDateTime;

    @Column(name = "return_date_time", nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime returnDateTime;

    @Column(length = 100)
    private String comments;

}
