package com.railgate.railgate.controller;

import com.railgate.railgate.dto.request.StatusReportRequest;
import com.railgate.railgate.dto.response.ApiResponse;
import com.railgate.railgate.service.StatusReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class StatusReportController {

    private final StatusReportService statusReportService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> submitReport(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody StatusReportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Report submitted",
                        statusReportService.submitReport(userDetails.getUsername(), request)));
    }

    @GetMapping("/crossing/{crossingId}")
    public ResponseEntity<ApiResponse<?>> getReportsByCrossing(@PathVariable Long crossingId) {
        return ResponseEntity.ok(ApiResponse.success(
                statusReportService.getReportsByCrossing(crossingId)));
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<ApiResponse<?>> resolveReport(@PathVariable Long id) {
        statusReportService.resolveReport(id);
        return ResponseEntity.ok(ApiResponse.success("Report resolved", null));
    }
}
