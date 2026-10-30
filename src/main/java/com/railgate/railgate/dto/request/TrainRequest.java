package com.railgate.railgate.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TrainRequest {
    @NotBlank private String trainNumber;
    @NotBlank private String trainName;
    @NotBlank private String fromStation;
    @NotBlank private String toStation;
}
