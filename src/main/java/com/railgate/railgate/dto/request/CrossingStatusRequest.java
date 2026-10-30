package com.railgate.railgate.dto.request;

import com.railgate.railgate.entity.Crossing;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrossingStatusRequest {
    @NotNull(message = "Status is required")
    private Crossing.Status status;
}
