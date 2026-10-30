package com.railgate.railgate.websocket;

import com.railgate.railgate.dto.response.WebSocketMessage;
import com.railgate.railgate.entity.Crossing;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    // Broadcast to all subscribers of a specific crossing
    public void broadcastCrossingUpdate(Crossing crossing) {
        WebSocketMessage message = WebSocketMessage.builder()
                .crossingId(crossing.getId())
                .crossingName(crossing.getName())
                .status(crossing.getStatus().name())
                .lastStatusChange(crossing.getLastStatusChange())
                .type("STATUS_UPDATE")
                .build();

        // Send to crossing-specific topic
        messagingTemplate.convertAndSend(
                "/topic/crossing/" + crossing.getId(), message);

        // Send to global topic (for map updates)
        messagingTemplate.convertAndSend("/topic/crossings/all", message);
    }

    // Send personal notification to a specific user
    public void sendPersonalNotification(String userEmail, WebSocketMessage message) {
        messagingTemplate.convertAndSendToUser(
                userEmail, "/queue/notifications", message);
    }
}
