package com.netceed.management.management_app.entity.shift;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netceed.management.management_app.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_SHIFT")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description", nullable = false, length = 50)
    private String description; //GENERAL, MORNING, AFTERNOON, NIGHT
    @Column(name = "start_time_shift", nullable = false)
    @JsonFormat(pattern = "HH:mm a", shape = JsonFormat.Shape.STRING)
    private LocalTime startTimeShift; //GENERAL: 09:00 AM  MORNING: 07:00 AM  AFTERNOON: 14:00 PM  NIGHT: 00:00 AM OF NEXT DAY
    @Column(name = "end_time_shift", nullable = false)
    @JsonFormat(pattern = "HH:mm a", shape = JsonFormat.Shape.STRING)
    private LocalTime endTimeShift; //GENERAL: 18:00 AM  MORNING: 15:30 PM  AFTERNOON: 00:00 AM OF NEXT DAY  NIGHT: 07:00 AM OF NEXT DAY
    @Column(name = "status", nullable = false)
    private boolean status; //ACTIVE, NOT ACTIVE

    public Shift(String description, LocalTime startTimeShift, LocalTime endTimeShift, boolean status){
        this.description = description;
        this.startTimeShift = startTimeShift;
        this.endTimeShift = endTimeShift;
        this.status = status;
    }

}
