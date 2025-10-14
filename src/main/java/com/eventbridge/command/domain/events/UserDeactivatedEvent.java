package com.eventbridge.command.domain.events;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDeactivatedEvent implements DomainEvent {
    private final String eventId;
    private final String userId;
    private final Instant occurredOn;

    public UserDeactivatedEvent(String userId) {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.userId = userId;
        this.occurredOn = Instant.now();
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public String getAggregateId() {
        return userId;
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String getEventType() {
        return "UserDeactivated";
    }
}