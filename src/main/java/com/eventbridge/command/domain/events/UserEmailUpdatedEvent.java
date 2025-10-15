package com.eventbridge.command.domain.events;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
public class UserEmailUpdatedEvent implements DomainEvent {
    private final String eventId;
    private final String userId;
    private final String newEmail;
    private final Instant occurredOn;

    @JsonCreator
    public UserEmailUpdatedEvent(
            @JsonProperty("eventId") String eventId,
            @JsonProperty("userId") String userId,
            @JsonProperty("newEmail") String newEmail,
            @JsonProperty("occurredOn") Instant occurredOn) {
        this.eventId = eventId;
        this.userId = userId;
        this.newEmail = newEmail;
        this.occurredOn = occurredOn;
    }

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
