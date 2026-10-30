package com.railgate.railgate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduleRequest {
    @NotNull private Long trainId;
    @NotNull private Long crossingId;
    @NotBlank private String scheduledTime;
    @NotBlank private String date;
}
