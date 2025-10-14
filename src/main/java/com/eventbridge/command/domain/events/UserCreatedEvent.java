package com.eventbridge.command.domain.events;

import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class UserCreatedEvent implements DomainEvent {
    private final String eventId;
    private final String userId;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final Instant occurredOn;

    public UserCreatedEvent(String userId, String username, String email, LocalDateTime createdAt) {
        this.eventId = UUID.randomUUID().toString();
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.occurredOn = Instant.now();
    }

    @Override
    public String getEventId() { return eventId; }

    @Override
    public String getAggregateId() { return userId; }

    @Override
    public Instant getOccurredOn() { return occurredOn; }

    @Override
    public String getEventType() { return "UserCreated"; }

}
