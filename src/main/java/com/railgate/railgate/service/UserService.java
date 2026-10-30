package com.railgate.railgate.service;

import com.railgate.railgate.dto.response.CrossingResponse;
import com.railgate.railgate.dto.response.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse getMe(String email);
    List<CrossingResponse> getMySubscriptions(String email);
    void subscribe(String email, Long crossingId);
    void unsubscribe(String email, Long crossingId);
    List<UserResponse> getAllUsers();
    void changeUserRole(Long userId, String role);
}
