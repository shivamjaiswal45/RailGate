package com.railgate.railgate.repository;

import com.railgate.railgate.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByDateOrderByScheduledTimeAsc(String date);

    List<Schedule> findByCrossingIdAndDateOrderByScheduledTimeAsc(Long crossingId, String date);

    List<Schedule> findByTrainIdAndDate(Long trainId, String date);

    @Query("SELECT s FROM Schedule s WHERE s.crossing.id = :crossingId " +
           "AND s.date = :date AND s.status IN ('UPCOMING', 'ACTIVE') " +
           "ORDER BY s.scheduledTime ASC")
    List<Schedule> findUpcomingByCrossingAndDate(@Param("crossingId") Long crossingId,
                                                  @Param("date") String date);

    @Query("SELECT COUNT(s) FROM Schedule s WHERE s.crossing.id = :crossingId " +
           "AND s.date = :date AND s.status = 'COMPLETED'")
    Long countCompletedByCrossingAndDate(@Param("crossingId") Long crossingId,
                                          @Param("date") String date);
}
