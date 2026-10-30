package com.railgate.railgate.service;

import com.railgate.railgate.dto.request.TrainRequest;
import com.railgate.railgate.dto.response.TrainResponse;
import java.util.List;

public interface TrainService {
    List<TrainResponse> getAllTrains();
    TrainResponse getTrainById(Long id);
    TrainResponse createTrain(TrainRequest request);
    TrainResponse updateTrain(Long id, TrainRequest request);
    void deleteTrain(Long id);
}
