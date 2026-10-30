package com.railgate.railgate.service.impl;

import com.railgate.railgate.dto.request.DelayRequest;
import com.railgate.railgate.dto.request.ScheduleRequest;
import com.railgate.railgate.dto.response.ScheduleResponse;
import com.railgate.railgate.entity.Crossing;
import com.railgate.railgate.entity.Schedule;
import com.railgate.railgate.entity.Train;
import com.railgate.railgate.exception.ResourceNotFoundException;
import com.railgate.railgate.repository.CrossingRepository;
import com.railgate.railgate.repository.ScheduleRepository;
import com.railgate.railgate.repository.TrainRepository;
import com.railgate.railgate.service.ScheduleService;
import com.railgate.railgate.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final CrossingRepository crossingRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<ScheduleResponse> getTodaySchedules() {
        String today = LocalDate.now().toString();
        return scheduleRepository.findByDateOrderByScheduledTimeAsc(today)
                .stream().map(mapperUtil::toScheduleResponse).toList();
    }

    @Override
    public List<ScheduleResponse> getSchedulesByCrossing(Long crossingId) {
        String today = LocalDate.now().toString();
        return scheduleRepository.findByCrossingIdAndDateOrderByScheduledTimeAsc(crossingId, today)
                .stream().map(mapperUtil::toScheduleResponse).toList();
    }

    @Override
    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest request) {
        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train", request.getTrainId()));
        Crossing crossing = crossingRepository.findById(request.getCrossingId())
                .orElseThrow(() -> new ResourceNotFoundException("Crossing", request.getCrossingId()));

        // Auto-calculate close and open times
        LocalDateTime base = LocalDateTime.parse(
                request.getDate() + "T" + request.getScheduledTime() + ":00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime closeTime = base.minusMinutes(3);
        LocalDateTime openTime = base.plusMinutes(crossing.getAvgClosureDurationMins());

        Schedule schedule = Schedule.builder()
                .train(train).crossing(crossing)
                .scheduledTime(request.getScheduledTime())
                .date(request.getDate())
                .estimatedCloseTime(closeTime)
                .estimatedOpenTime(openTime)
                .build();

        return mapperUtil.toScheduleResponse(scheduleRepository.save(schedule));
    }

    @Override
    @Transactional
    public ScheduleResponse markDelay(Long scheduleId, DelayRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", scheduleId));
        schedule.setDelayMins(request.getDelayMins());
        schedule.setStatus(Schedule.Status.DELAYED);

        // Recalculate times based on delay
        if (schedule.getEstimatedCloseTime() != null) {
            schedule.setEstimatedCloseTime(
                    schedule.getEstimatedCloseTime().plusMinutes(request.getDelayMins()));
            schedule.setEstimatedOpenTime(
                    schedule.getEstimatedOpenTime().plusMinutes(request.getDelayMins()));
        }
        return mapperUtil.toScheduleResponse(scheduleRepository.save(schedule));
    }
}
