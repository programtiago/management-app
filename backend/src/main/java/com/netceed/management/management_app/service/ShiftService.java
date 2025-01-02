package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.Shift;
import com.netceed.management.management_app.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftService{

    private final ShiftRepository shiftRepository;

    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }
}
