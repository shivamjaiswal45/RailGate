package com.railgate.railgate.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DelayRequest {
    @NotNull @Min(1)
    private Integer delayMins;
}
