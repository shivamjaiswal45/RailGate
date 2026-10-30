package com.railgate.railgate.service;

import com.railgate.railgate.dto.request.LoginRequest;
import com.railgate.railgate.dto.request.RegisterRequest;
import com.railgate.railgate.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
}
