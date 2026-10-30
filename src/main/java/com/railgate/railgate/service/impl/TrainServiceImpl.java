package com.railgate.railgate.service.impl;

import com.railgate.railgate.dto.request.TrainRequest;
import com.railgate.railgate.dto.response.TrainResponse;
import com.railgate.railgate.entity.Train;
import com.railgate.railgate.exception.DuplicateResourceException;
import com.railgate.railgate.exception.ResourceNotFoundException;
import com.railgate.railgate.repository.TrainRepository;
import com.railgate.railgate.service.TrainService;
import com.railgate.railgate.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<TrainResponse> getAllTrains() {
        return trainRepository.findByIsActiveTrue()
                .stream().map(mapperUtil::toTrainResponse).toList();
    }

    @Override
    public TrainResponse getTrainById(Long id) {
        return mapperUtil.toTrainResponse(trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train", id)));
    }

    @Override
    @Transactional
    public TrainResponse createTrain(TrainRequest request) {
        if (trainRepository.existsByTrainNumber(request.getTrainNumber())) {
            throw new DuplicateResourceException("Train " + request.getTrainNumber() + " already exists");
        }
        Train train = Train.builder()
                .trainNumber(request.getTrainNumber())
                .trainName(request.getTrainName())
                .fromStation(request.getFromStation())
                .toStation(request.getToStation())
                .build();
        return mapperUtil.toTrainResponse(trainRepository.save(train));
    }

    @Override
    @Transactional
    public TrainResponse updateTrain(Long id, TrainRequest request) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train", id));
        train.setTrainName(request.getTrainName());
        train.setFromStation(request.getFromStation());
        train.setToStation(request.getToStation());
        return mapperUtil.toTrainResponse(trainRepository.save(train));
    }

    @Override
    @Transactional
    public void deleteTrain(Long id) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train", id));
        train.setIsActive(false);
        trainRepository.save(train);
    }
}
