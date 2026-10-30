package com.railgate.railgate.service.impl;

import com.railgate.railgate.dto.response.CrossingResponse;
import com.railgate.railgate.dto.response.UserResponse;
import com.railgate.railgate.entity.Crossing;
import com.railgate.railgate.entity.User;
import com.railgate.railgate.exception.ResourceNotFoundException;
import com.railgate.railgate.repository.CrossingRepository;
import com.railgate.railgate.repository.UserRepository;
import com.railgate.railgate.service.UserService;
import com.railgate.railgate.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CrossingRepository crossingRepository;
    private final MapperUtil mapperUtil;

    @Override
    public UserResponse getMe(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapperUtil.toUserResponse(user);
    }


    @Override
    @Transactional(readOnly = true)
    public List<CrossingResponse> getMySubscriptions(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getSubscribedCrossings().stream()
                .map(mapperUtil::toCrossingResponse)
                .toList();
    }


    @Override
    @Transactional
    public void subscribe(String email, Long crossingId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Crossing crossing = crossingRepository.findById(crossingId)
                .orElseThrow(() -> new ResourceNotFoundException("Crossing", crossingId));
        if (!user.getSubscribedCrossings().contains(crossing)) {
            user.getSubscribedCrossings().add(crossing);
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void unsubscribe(String email, Long crossingId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.getSubscribedCrossings().removeIf(c -> c.getId().equals(crossingId));
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(mapperUtil::toUserResponse).toList();
    }

    @Override
    @Transactional
    public void changeUserRole(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        user.setRole(User.Role.valueOf(role.toUpperCase()));
        userRepository.save(user);
    }
}
