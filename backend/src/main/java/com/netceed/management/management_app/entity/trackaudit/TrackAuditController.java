package com.netceed.management.management_app.entity.trackaudit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/track-audits")
@RequiredArgsConstructor
public class TrackAuditController {

    private final TrackAuditService trackAuditService;
    private final TrackAuditMapper trackAuditMapper;

    @GetMapping
    public List<TrackAuditDto> getAll(){
        return trackAuditMapper.convertListTrackAuditToListTrackAuditDto(trackAuditService.getAllTrackAudits());
    }
}
