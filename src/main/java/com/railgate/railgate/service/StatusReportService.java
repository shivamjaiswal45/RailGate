package com.railgate.railgate.service;

import com.railgate.railgate.dto.request.StatusReportRequest;
import com.railgate.railgate.dto.response.StatusReportResponse;
import java.util.List;

public interface StatusReportService {
    StatusReportResponse submitReport(String email, StatusReportRequest request);
    List<StatusReportResponse> getReportsByCrossing(Long crossingId);
    void resolveReport(Long reportId);
}
