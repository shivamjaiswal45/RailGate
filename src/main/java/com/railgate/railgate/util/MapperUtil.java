package com.railgate.railgate.util;

import com.railgate.railgate.dto.response.*;
import com.railgate.railgate.entity.*;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    public CrossingResponse toCrossingResponse(Crossing c) {
        return CrossingResponse.builder()
                .id(c.getId()).name(c.getName()).code(c.getCode())
                .city(c.getCity()).state(c.getState()).highway(c.getHighway())
                .latitude(c.getLatitude()).longitude(c.getLongitude())
                .status(c.getStatus().name())
                .avgClosureDurationMins(c.getAvgClosureDurationMins())
                .lastStatusChange(c.getLastStatusChange())
                .isActive(c.getIsActive())
                .build();
    }

    public TrainResponse toTrainResponse(Train t) {
        return TrainResponse.builder()
                .id(t.getId()).trainNumber(t.getTrainNumber())
                .trainName(t.getTrainName())
                .fromStation(t.getFromStation()).toStation(t.getToStation())
                .isActive(t.getIsActive())
                .build();
    }

    public ScheduleResponse toScheduleResponse(Schedule s) {
        return ScheduleResponse.builder()
                .id(s.getId())
                .train(toTrainResponse(s.getTrain()))
                .crossing(toCrossingResponse(s.getCrossing()))
                .scheduledTime(s.getScheduledTime())
                .estimatedCloseTime(s.getEstimatedCloseTime())
                .estimatedOpenTime(s.getEstimatedOpenTime())
                .status(s.getStatus().name())
                .delayMins(s.getDelayMins())
                .date(s.getDate())
                .build();
    }

    public UserResponse toUserResponse(User u) {
        return UserResponse.builder()
                .id(u.getId()).name(u.getName()).email(u.getEmail())
                .role(u.getRole().name()).isActive(u.getIsActive())
                .createdAt(u.getCreatedAt())
                .build();
    }

    public StatusReportResponse toStatusReportResponse(StatusReport r) {
        return StatusReportResponse.builder()
                .id(r.getId())
                .reportedStatus(r.getReportedStatus().name())
                .reason(r.getReason())
                .isResolved(r.getIsResolved())
                .userName(r.getUser().getName())
                .createdAt(r.getCreatedAt())
                .build();
    }

    public NotificationResponse toNotificationResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId()).message(n.getMessage())
                .isRead(n.getIsRead())
                .crossingName(n.getCrossing() != null ? n.getCrossing().getName() : null)
                .createdAt(n.getCreatedAt())
                .build();
    }
}
