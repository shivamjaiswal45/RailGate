package com.railgate.railgate.service.impl;

import com.railgate.railgate.dto.request.StatusReportRequest;
import com.railgate.railgate.dto.response.StatusReportResponse;
import com.railgate.railgate.entity.Crossing;
import com.railgate.railgate.entity.StatusReport;
import com.railgate.railgate.entity.User;
import com.railgate.railgate.exception.ResourceNotFoundException;
import com.railgate.railgate.repository.CrossingRepository;
import com.railgate.railgate.repository.StatusReportRepository;
import com.railgate.railgate.repository.UserRepository;
import com.railgate.railgate.service.StatusReportService;
import com.railgate.railgate.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusReportServiceImpl implements StatusReportService {

    private final StatusReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CrossingRepository crossingRepository;
    private final MapperUtil mapperUtil;

    @Override
    @Transactional
    public StatusReportResponse submitReport(String email, StatusReportRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Crossing crossing = crossingRepository.findById(request.getCrossingId())
                .orElseThrow(() -> new ResourceNotFoundException("Crossing", request.getCrossingId()));

        StatusReport report = StatusReport.builder()
                .user(user).crossing(crossing)
                .reportedStatus(request.getReportedStatus())
                .reason(request.getReason())
                .build();
        return mapperUtil.toStatusReportResponse(reportRepository.save(report));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusReportResponse> getReportsByCrossing(Long crossingId) {
        return reportRepository.findByCrossingIdAndIsResolvedFalse(crossingId)
                .stream()
                .map(mapperUtil::toStatusReportResponse)
                .toList();
    }

    @Override
    @Transactional
    public void resolveReport(Long reportId) {
        StatusReport report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report", reportId));
        report.setIsResolved(true);
        reportRepository.save(report);
    }
}
