package com.netceed.management.management_app.entity.trackaudit;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TrackAuditDto(Long id, @NotNull Long entityId, @NotBlank String action, @NotNull @JsonFormat(pattern="dd-MM-yyyy'T'HH:mm:ss") LocalDateTime actionDateTime, @NotBlank String username, @NotBlank String entity) {

}
