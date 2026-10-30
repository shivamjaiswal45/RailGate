package com.railgate.railgate.controller;

import com.railgate.railgate.dto.request.CrossingRequest;
import com.railgate.railgate.dto.request.CrossingStatusRequest;
import com.railgate.railgate.dto.response.ApiResponse;
import com.railgate.railgate.service.CrossingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crossings")
@RequiredArgsConstructor
public class CrossingController {

    private final CrossingService crossingService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllCrossings() {
        return ResponseEntity.ok(ApiResponse.success(crossingService.getAllCrossings()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getCrossingById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(crossingService.getCrossingById(id)));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponse<?>> getCrossingsByCity(@PathVariable String city) {
        return ResponseEntity.ok(ApiResponse.success(crossingService.getCrossingsByCity(city)));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<ApiResponse<?>> getCrossingsByState(@PathVariable String state) {
        return ResponseEntity.ok(ApiResponse.success(crossingService.getCrossingsByState(state)));
    }

    @GetMapping("/nearby")
    public ResponseEntity<ApiResponse<?>> getNearbyCrossings(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "10") Double radiusKm) {
        return ResponseEntity.ok(ApiResponse.success(
                crossingService.getNearbyCrossings(lat, lng, radiusKm)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createCrossing(
            @Valid @RequestBody CrossingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Crossing created",
                        crossingService.createCrossing(request)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<?>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody CrossingStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Status updated",
                crossingService.updateCrossingStatus(id, request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateCrossing(
            @PathVariable Long id,
            @Valid @RequestBody CrossingRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Crossing updated",
                crossingService.updateCrossing(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCrossing(@PathVariable Long id) {
        crossingService.deleteCrossing(id);
        return ResponseEntity.ok(ApiResponse.success("Crossing deactivated", null));
    }
}
