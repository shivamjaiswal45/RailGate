package com.railgate.railgate.controller;

import com.railgate.railgate.dto.response.ApiResponse;
import com.railgate.railgate.service.NotificationService;
import com.railgate.railgate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final NotificationService notificationService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> getMe(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success(
                userService.getMe(userDetails.getUsername())));
    }

    @GetMapping("/me/subscriptions")
    public ResponseEntity<ApiResponse<?>> getMySubscriptions(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success(
                userService.getMySubscriptions(userDetails.getUsername())));
    }

    @PostMapping("/me/subscriptions/{crossingId}")
    public ResponseEntity<ApiResponse<?>> subscribe(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long crossingId) {
        userService.subscribe(userDetails.getUsername(), crossingId);
        return ResponseEntity.ok(ApiResponse.success("Subscribed successfully", null));
    }

    @DeleteMapping("/me/subscriptions/{crossingId}")
    public ResponseEntity<ApiResponse<?>> unsubscribe(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long crossingId) {
        userService.unsubscribe(userDetails.getUsername(), crossingId);
        return ResponseEntity.ok(ApiResponse.success("Unsubscribed successfully", null));
    }

    @GetMapping("/me/notifications")
    public ResponseEntity<ApiResponse<?>> getNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success(
                notificationService.getMyNotifications(userDetails.getUsername())));
    }

    @GetMapping("/me/notifications/unread-count")
    public ResponseEntity<ApiResponse<?>> getUnreadCount(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success(
                notificationService.getUnreadCount(userDetails.getUsername())));
    }

    @PatchMapping("/me/notifications/read-all")
    public ResponseEntity<ApiResponse<?>> markAllRead(
            @AuthenticationPrincipal UserDetails userDetails) {
        notificationService.markAllRead(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read", null));
    }
}
