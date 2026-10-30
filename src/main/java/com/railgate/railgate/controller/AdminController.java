package com.railgate.railgate.controller;

import com.railgate.railgate.dto.response.ApiResponse;
import com.railgate.railgate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(userService.getAllUsers()));
    }

    @PatchMapping("/users/{userId}/role")
    public ResponseEntity<ApiResponse<?>> changeRole(
            @PathVariable Long userId,
            @RequestParam String role) {
        userService.changeUserRole(userId, role);
        return ResponseEntity.ok(ApiResponse.success("Role updated", null));
    }
}
