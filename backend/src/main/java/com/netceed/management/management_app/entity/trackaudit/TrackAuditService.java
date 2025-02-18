package com.netceed.management.management_app.entity.trackaudit;

import com.netceed.management.management_app.repository.TrackAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackAuditService {

    private final TrackAuditRepository trackAuditRepository;

    public List<TrackAudit> getAllTrackAudits(){
        return trackAuditRepository.findAll();
    }

    public TrackAudit create(TrackAudit trackAuditDto){
        return trackAuditRepository.save(trackAuditDto);
    }
}
