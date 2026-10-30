package com.railgate.railgate.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crossing_id", nullable = false)
    private Crossing crossing;

    @Column(name = "scheduled_time", nullable = false)
    private String scheduledTime;

    @Column(name = "estimated_close_time")
    private LocalDateTime estimatedCloseTime;

    @Column(name = "estimated_open_time")
    private LocalDateTime estimatedOpenTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.UPCOMING;

    @Column(name = "delay_mins")
    @Builder.Default
    private Integer delayMins = 0;

    @Column(nullable = false)
    private String date;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status {
        UPCOMING, ACTIVE, COMPLETED, DELAYED
    }
}
