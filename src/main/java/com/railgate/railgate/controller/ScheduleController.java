package com.railgate.railgate.controller;

import com.railgate.railgate.dto.request.DelayRequest;
import com.railgate.railgate.dto.request.ScheduleRequest;
import com.railgate.railgate.dto.response.ApiResponse;
import com.railgate.railgate.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/today")
    public ResponseEntity<ApiResponse<?>> getTodaySchedules() {
        return ResponseEntity.ok(ApiResponse.success(scheduleService.getTodaySchedules()));
    }

    @GetMapping("/crossing/{crossingId}")
    public ResponseEntity<ApiResponse<?>> getSchedulesByCrossing(@PathVariable Long crossingId) {
        return ResponseEntity.ok(ApiResponse.success(
                scheduleService.getSchedulesByCrossing(crossingId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createSchedule(
            @Valid @RequestBody ScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Schedule created",
                        scheduleService.createSchedule(request)));
    }

    @PatchMapping("/{id}/delay")
    public ResponseEntity<ApiResponse<?>> markDelay(
            @PathVariable Long id, @Valid @RequestBody DelayRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Delay recorded",
                scheduleService.markDelay(id, request)));
    }
}
