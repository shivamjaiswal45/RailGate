package com.railgate.railgate.service;

import com.railgate.railgate.dto.request.CrossingRequest;
import com.railgate.railgate.dto.request.CrossingStatusRequest;
import com.railgate.railgate.dto.response.CrossingDetailResponse;
import com.railgate.railgate.dto.response.CrossingResponse;
import java.util.List;

public interface CrossingService {
    List<CrossingResponse> getAllCrossings();
    CrossingDetailResponse getCrossingById(Long id);
    List<CrossingResponse> getCrossingsByCity(String city);
    List<CrossingResponse> getCrossingsByState(String state);
    List<CrossingResponse> getNearbyCrossings(Double lat, Double lng, Double radiusKm);
    CrossingResponse createCrossing(CrossingRequest request);
    CrossingResponse updateCrossingStatus(Long id, CrossingStatusRequest request);
    CrossingResponse updateCrossing(Long id, CrossingRequest request);
    void deleteCrossing(Long id);
}
