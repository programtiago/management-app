package com.netceed.management.management_app.entity.shift;

import com.netceed.management.management_app.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shifts")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @GetMapping("/all")
    public ResponseEntity<List<Shift>> getAllShifts(){
        return ResponseEntity.ok(shiftService.getAllShifts());
    }
}
