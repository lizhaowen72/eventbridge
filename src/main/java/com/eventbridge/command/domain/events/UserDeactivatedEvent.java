package com.eventbridge.command.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
public class UserDeactivatedEvent implements DomainEvent {
    private final String eventId;
    private final String userId;
    private final Instant occurredOn;

    // Jackson 反序列化构造方法
    @JsonCreator
    public UserDeactivatedEvent(
            @JsonProperty("eventId") String eventId,
            @JsonProperty("userId") String userId,
            @JsonProperty("occurredOn") Instant occurredOn) {
        this.eventId = eventId;
        this.userId = userId;
        this.occurredOn = occurredOn;
    }

    // 业务构造方法
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDeactivatedEvent that = (UserDeactivatedEvent) o;
        return Objects.equals(eventId, that.eventId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(occurredOn, that.occurredOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, userId, occurredOn);
    }

    @Override
    public String toString() {
        return "UserDeactivatedEvent{" +
                "eventId='" + eventId + '\'' +
                ", userId='" + userId + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}