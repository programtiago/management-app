package com.netceed.management.management_app.entity.trackaudit;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrackAuditMapper {

    public TrackAuditDto toDto(TrackAudit trackAudit){
        if (trackAudit == null){
            return null;
        }

        return new TrackAuditDto(trackAudit.getId(), trackAudit.getAction(), trackAudit.getActionDateTime(), trackAudit.getEntity());
    }

    public TrackAudit toEntity(TrackAuditDto trackAuditDto){
        TrackAudit trackAudit = new TrackAudit(trackAuditDto.id(), trackAuditDto.action(), trackAuditDto.actionDateTime(),
                trackAuditDto.entity());

        if (trackAuditDto.id() != null){
            trackAudit.setId(trackAuditDto.id());
        }

        return trackAudit;
    }

    public List<TrackAuditDto> convertListTrackAuditToListTrackAuditDto(List<TrackAudit> trackAudits){
        List<TrackAuditDto> trackAuditDtos = new ArrayList<>();
        for (TrackAudit trackAudit : trackAudits){
            TrackAuditDto trackAuditDto = new TrackAuditDto(trackAudit.getId(), trackAudit.getAction(),
                    trackAudit.getActionDateTime(), trackAudit.getEntity());
            trackAuditDtos.add(trackAuditDto);
        }
        return trackAuditDtos;
    }
}
