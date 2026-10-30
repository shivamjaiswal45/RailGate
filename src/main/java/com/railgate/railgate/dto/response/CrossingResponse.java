package com.railgate.railgate.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class CrossingResponse {
    private Long id;
    private String name;
    private String code;
    private String city;
    private String state;
    private String highway;
    private Double latitude;
    private Double longitude;
    private String status;
    private Integer avgClosureDurationMins;
    private LocalDateTime lastStatusChange;
    private Boolean isActive;
}
