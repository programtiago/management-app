package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.Shift;
import com.netceed.management.management_app.repository.ShiftRepository;
import com.netceed.management.management_app.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;

    @Override
    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }
}
