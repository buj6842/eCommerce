package com.hhplus.ecommerce.domain.outbox;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @Builder
    private OutboxEvent(EventType eventType, String payload, OutboxStatus status) {
        this.eventType = eventType;
        this.payload = payload;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public enum OutboxStatus {
        INIT,
        SUCCESS,
        FAILED
    }
    public enum EventType {
        ORDER,
        PRODUCT,
        USER
    }

    public void successStatus() {
        this.status = OutboxStatus.SUCCESS;
    }

    public void failedStatus() {
        this.status = OutboxStatus.FAILED;
    }
}
