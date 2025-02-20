package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.trackaudit.TrackAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackAuditRepository extends JpaRepository<TrackAudit, Long> {

    @Query("SELECT t FROM TrackAudit t WHERE t.entity LIKE '%User%'")
    List<TrackAudit> getAllTrackAuditsByUserEntity();

    @Query("SELECT t FROM TrackAudit t WHERE t.entity LIKE '%entity%'")
    List<TrackAudit> findAllTrackAuditsByEntity(String entity);

    @Query("SELECT t FROM TrackAudit t WHERE t.entitysId = :entitysId")
    List<TrackAudit> findAllTrackAuditsByEntitysId(@Param("entitysId") List<Long> entitysId);
}
