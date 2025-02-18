package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.trackaudit.TrackAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackAuditRepository extends JpaRepository<TrackAudit, Long> {
}
