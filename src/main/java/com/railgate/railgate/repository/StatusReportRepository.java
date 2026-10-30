package com.railgate.railgate.repository;

import com.railgate.railgate.entity.StatusReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StatusReportRepository extends JpaRepository<StatusReport, Long> {
    List<StatusReport> findByCrossingIdAndIsResolvedFalse(Long crossingId);
    long countByCrossingIdAndIsResolvedFalse(Long crossingId);
}
