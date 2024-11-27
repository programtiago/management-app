package com.netceed.management.management_app.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

public record ShiftDto (Long id, LocalDateTime startDateTimeShift, LocalDateTime endDateTimeShift){ }
