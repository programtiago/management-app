package com.netceed.management.management_app.entity.trackaudit;

import com.netceed.management.management_app.repository.TrackAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackAuditService {

    private final TrackAuditRepository trackAuditRepository;

    public List<TrackAudit> getAllTrackAudits(){
        return trackAuditRepository.findAll();
    }
    public void logAction(List<Long> entitysId, String action, String username, String entity){
        TrackAudit trackAudit = new TrackAudit();
        trackAudit.setEntity(entity);
        trackAudit.setEntitysId(entitysId);
        trackAudit.setAction(action);
        trackAudit.setUsername(username);
        trackAudit.setActionDateTime(LocalDateTime.now());

        trackAuditRepository.save(trackAudit);
    }
}
