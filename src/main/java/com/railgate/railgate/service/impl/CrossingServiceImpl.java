package com.railgate.railgate.service.impl;

import com.railgate.railgate.dto.request.CrossingRequest;
import com.railgate.railgate.dto.request.CrossingStatusRequest;
import com.railgate.railgate.dto.response.CrossingDetailResponse;
import com.railgate.railgate.dto.response.CrossingResponse;
import com.railgate.railgate.dto.response.ScheduleResponse;
import com.railgate.railgate.entity.Crossing;
import com.railgate.railgate.exception.DuplicateResourceException;
import com.railgate.railgate.exception.ResourceNotFoundException;
import com.railgate.railgate.repository.CrossingRepository;
import com.railgate.railgate.repository.ScheduleRepository;
import com.railgate.railgate.repository.StatusReportRepository;
import com.railgate.railgate.service.CrossingService;
import com.railgate.railgate.util.MapperUtil;
import com.railgate.railgate.websocket.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrossingServiceImpl implements CrossingService {

    private final CrossingRepository crossingRepository;
    private final ScheduleRepository scheduleRepository;
    private final StatusReportRepository statusReportRepository;
    private final WebSocketService webSocketService;
    private final MapperUtil mapperUtil;

    @Override
    public List<CrossingResponse> getAllCrossings() {
        return crossingRepository.findByIsActiveTrue()
                .stream().map(mapperUtil::toCrossingResponse).toList();
    }

    @Override
    public CrossingDetailResponse getCrossingById(Long id) {
        Crossing crossing = crossingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Crossing", id));

        String today = LocalDate.now().toString();
        List<ScheduleResponse> schedules = scheduleRepository
                .findUpcomingByCrossingAndDate(id, today)
                .stream().map(mapperUtil::toScheduleResponse).toList();

        long pendingReports = statusReportRepository.countByCrossingIdAndIsResolvedFalse(id);
        long totalClosures = scheduleRepository.countCompletedByCrossingAndDate(id, today);

        return CrossingDetailResponse.builder()
                .crossing(mapperUtil.toCrossingResponse(crossing))
                .todaySchedules(schedules)
                .pendingReports(pendingReports)
                .totalClosuresToday(totalClosures)
                .build();
    }

    @Override
    public List<CrossingResponse> getCrossingsByCity(String city) {
        return crossingRepository.findByCityAndIsActiveTrue(city)
                .stream().map(mapperUtil::toCrossingResponse).toList();
    }

    @Override
    public List<CrossingResponse> getCrossingsByState(String state) {
        return crossingRepository.findByStateAndIsActiveTrue(state)
                .stream().map(mapperUtil::toCrossingResponse).toList();
    }

    @Override
    public List<CrossingResponse> getNearbyCrossings(Double lat, Double lng, Double radiusKm) {
        return crossingRepository.findNearbyCrossings(lat, lng, radiusKm)
                .stream().map(mapperUtil::toCrossingResponse).toList();
    }

    @Override
    @Transactional
    public CrossingResponse createCrossing(CrossingRequest request) {
        if (crossingRepository.findByCode(request.getCode()).isPresent()) {
            throw new DuplicateResourceException("Crossing with code " + request.getCode() + " already exists");
        }
        Crossing crossing = Crossing.builder()
                .name(request.getName()).code(request.getCode())
                .city(request.getCity()).state(request.getState())
                .highway(request.getHighway())
                .latitude(request.getLatitude()).longitude(request.getLongitude())
                .avgClosureDurationMins(request.getAvgClosureDurationMins())
                .build();
        return mapperUtil.toCrossingResponse(crossingRepository.save(crossing));
    }

    @Override
    @Transactional
    public CrossingResponse updateCrossingStatus(Long id, CrossingStatusRequest request) {
        Crossing crossing = crossingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Crossing", id));
        crossing.setStatus(request.getStatus());
        crossing.setLastStatusChange(LocalDateTime.now());
        Crossing saved = crossingRepository.save(crossing);

        // Broadcast via WebSocket to all subscribers
        webSocketService.broadcastCrossingUpdate(saved);

        return mapperUtil.toCrossingResponse(saved);
    }

    @Override
    @Transactional
    public CrossingResponse updateCrossing(Long id, CrossingRequest request) {
        Crossing crossing = crossingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Crossing", id));
        crossing.setName(request.getName());
        crossing.setCity(request.getCity());
        crossing.setState(request.getState());
        crossing.setHighway(request.getHighway());
        crossing.setLatitude(request.getLatitude());
        crossing.setLongitude(request.getLongitude());
        crossing.setAvgClosureDurationMins(request.getAvgClosureDurationMins());
        return mapperUtil.toCrossingResponse(crossingRepository.save(crossing));
    }

    @Override
    @Transactional
    public void deleteCrossing(Long id) {
        Crossing crossing = crossingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Crossing", id));
        crossing.setIsActive(false);
        crossingRepository.save(crossing);
    }
}
