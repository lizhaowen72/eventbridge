package com.eventbridge.command.domain.events;

import java.time.Instant;

public interface DomainEvent {
    String getEventId();
    String getAggregateId();
    Instant getOccurredOn();
    String getEventType();
}
