package com.railgate.railgate.service;

import com.railgate.railgate.dto.request.DelayRequest;
import com.railgate.railgate.dto.request.ScheduleRequest;
import com.railgate.railgate.dto.response.ScheduleResponse;
import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> getTodaySchedules();
    List<ScheduleResponse> getSchedulesByCrossing(Long crossingId);
    ScheduleResponse createSchedule(ScheduleRequest request);
    ScheduleResponse markDelay(Long scheduleId, DelayRequest request);
}
