package com.netceed.management.management_app.entity.shift;

import java.time.LocalDateTime;

public record ShiftDto (Long id, LocalDateTime startDateTimeShift, LocalDateTime endDateTimeShift){ }
