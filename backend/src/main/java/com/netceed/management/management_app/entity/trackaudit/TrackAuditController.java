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

    @GetMapping("/user")
    public List<TrackAuditDto> getTrackAuditsByUserEntity(){
        return trackAuditMapper.convertListTrackAuditToListTrackAuditDto(trackAuditService.getTrackAuditsByUserEntity());
    }

    @GetMapping("/by-entitys")
    public List<TrackAuditDto> getTrackAuditsByEntitysIds(@RequestBody List<Long> entitysId){
        return trackAuditMapper.convertListTrackAuditToListTrackAuditDto(trackAuditService.getTrackAuditsByEntitysId(entitysId));
    }
}
