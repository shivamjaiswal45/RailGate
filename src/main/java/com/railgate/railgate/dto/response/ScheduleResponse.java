package com.railgate.railgate.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class ScheduleResponse {
    private Long id;
    private TrainResponse train;
    private CrossingResponse crossing;
    private String scheduledTime;
    private LocalDateTime estimatedCloseTime;
    private LocalDateTime estimatedOpenTime;
    private String status;
    private Integer delayMins;
    private String date;
}
