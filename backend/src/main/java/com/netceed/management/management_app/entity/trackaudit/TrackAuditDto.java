package com.netceed.management.management_app.entity.trackaudit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TrackAuditDto(Long id, @NotBlank String action, @NotNull LocalDateTime actionDateTime, @NotBlank String entity) {

}
