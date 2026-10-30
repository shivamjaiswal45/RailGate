package com.railgate.railgate.service.impl;

import com.railgate.railgate.dto.response.NotificationResponse;
import com.railgate.railgate.entity.Crossing;
import com.railgate.railgate.entity.Notification;
import com.railgate.railgate.entity.User;
import com.railgate.railgate.exception.ResourceNotFoundException;
import com.railgate.railgate.repository.NotificationRepository;
import com.railgate.railgate.repository.UserRepository;
import com.railgate.railgate.service.NotificationService;
import com.railgate.railgate.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    @Override
    public List<NotificationResponse> getMyNotifications(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream().map(mapperUtil::toNotificationResponse).toList();
    }

    @Override
    public long getUnreadCount(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return notificationRepository.countByUserIdAndIsReadFalse(user.getId());
    }

    @Override
    @Transactional
    public void markAllRead(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Notification> unread = notificationRepository.findByUserIdAndIsReadFalse(user.getId());
        unread.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(unread);
    }

    @Override
    @Transactional
    public void notifySubscribers(Crossing crossing, String message) {
        crossing.getId();
        List<User> subscribers = userRepository.findAll().stream()
                .filter(u -> u.getSubscribedCrossings().stream()
                        .anyMatch(c -> c.getId().equals(crossing.getId())))
                .toList();

        List<Notification> notifications = subscribers.stream()
                .map(user -> Notification.builder()
                        .user(user).crossing(crossing).message(message).build())
                .toList();
        notificationRepository.saveAll(notifications);
    }
}
