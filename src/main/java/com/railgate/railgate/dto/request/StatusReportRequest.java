package com.railgate.railgate.dto.request;

import com.railgate.railgate.entity.Crossing;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusReportRequest {
    @NotNull private Long crossingId;
    @NotNull private Crossing.Status reportedStatus;
    private String reason;
}
