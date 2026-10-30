package com.railgate.railgate.controller;

import com.railgate.railgate.dto.request.TrainRequest;
import com.railgate.railgate.dto.response.ApiResponse;
import com.railgate.railgate.service.TrainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllTrains() {
        return ResponseEntity.ok(ApiResponse.success(trainService.getAllTrains()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getTrainById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(trainService.getTrainById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTrain(@Valid @RequestBody TrainRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Train created", trainService.createTrain(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateTrain(
            @PathVariable Long id, @Valid @RequestBody TrainRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Train updated",
                trainService.updateTrain(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteTrain(@PathVariable Long id) {
        trainService.deleteTrain(id);
        return ResponseEntity.ok(ApiResponse.success("Train deactivated", null));
    }
}
