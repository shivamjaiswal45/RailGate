package com.railgate.railgate.service;

import com.railgate.railgate.dto.response.NotificationResponse;
import com.railgate.railgate.entity.Crossing;
import com.railgate.railgate.entity.User;
import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getMyNotifications(String email);
    long getUnreadCount(String email);
    void markAllRead(String email);
    void notifySubscribers(Crossing crossing, String message);
}
