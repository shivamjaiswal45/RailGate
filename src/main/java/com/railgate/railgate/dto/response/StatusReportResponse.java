package com.railgate.railgate.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class StatusReportResponse {
    private Long id;
    private String reportedStatus;
    private String reason;
    private Boolean isResolved;
    private String userName;
    private LocalDateTime createdAt;
}
