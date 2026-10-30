package com.railgate.railgate.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class WebSocketMessage {
    private Long crossingId;
    private String crossingName;
    private String status;
    private LocalDateTime lastStatusChange;
    private String type;
}
