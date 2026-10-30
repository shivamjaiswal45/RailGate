package com.railgate.railgate.repository;

import com.railgate.railgate.entity.Crossing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CrossingRepository extends JpaRepository<Crossing, Long> {

    List<Crossing> findByIsActiveTrue();

    List<Crossing> findByCityAndIsActiveTrue(String city);

    List<Crossing> findByStateAndIsActiveTrue(String state);

    List<Crossing> findByStatusAndIsActiveTrue(Crossing.Status status);

    Optional<Crossing> findByCode(String code);

    @Query("SELECT c FROM Crossing c WHERE c.isActive = true AND " +
           "(6371 * acos(cos(radians(:lat)) * cos(radians(c.latitude)) * " +
           "cos(radians(c.longitude) - radians(:lng)) + " +
           "sin(radians(:lat)) * sin(radians(c.latitude)))) < :radiusKm")
    List<Crossing> findNearbyCrossings(@Param("lat") Double lat,
                                        @Param("lng") Double lng,
                                        @Param("radiusKm") Double radiusKm);
}
