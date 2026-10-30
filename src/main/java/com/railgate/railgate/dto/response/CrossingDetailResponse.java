package com.railgate.railgate.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data @Builder
public class CrossingDetailResponse {
    private CrossingResponse crossing;
    private List<ScheduleResponse> todaySchedules;
    private Long pendingReports;
    private Long totalClosuresToday;
}
