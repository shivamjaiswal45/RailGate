package com.railgate.railgate.dto.response;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class TrainResponse {
    private Long id;
    private String trainNumber;
    private String trainName;
    private String fromStation;
    private String toStation;
    private Boolean isActive;
}
