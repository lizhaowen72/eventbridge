package com.eventbridge.command.domain.events;


import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserEmailUpdatedEvent implements DomainEvent {
    private final String eventId;
    private final String userId;
    private final String newEmail;
    private final Instant occurredOn;

    public UserEmailUpdatedEvent(String userId, String newEmail) {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.userId = userId;
        this.newEmail = newEmail;
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
        return "UserEmailUpdated";
    }
}
