package com.eventbridge.command.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;


@Getter
@Setter
public class UserCreatedEvent implements DomainEvent {
    private final String eventId;
    private final String userId;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final Instant occurredOn;

@JsonCreator
public UserCreatedEvent(
        @JsonProperty("eventId") String eventId,
        @JsonProperty("userId") String userId,
        @JsonProperty("username") String username,
        @JsonProperty("email") String email,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("occurredOn") Instant occurredOn) {
    this.eventId = eventId;
    this.userId = userId;
    this.username = username;
    this.email = email;
    this.createdAt = createdAt;
    this.occurredOn = occurredOn;
}

public UserCreatedEvent(String userId, String username, String email, LocalDateTime createdAt) {
    this.eventId = java.util.UUID.randomUUID().toString();
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
