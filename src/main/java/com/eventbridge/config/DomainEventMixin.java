package com.eventbridge.config;

import com.eventbridge.command.domain.events.UserCreatedEvent;
import com.eventbridge.command.domain.events.UserEmailUpdatedEvent;
import com.eventbridge.command.domain.events.UserDeactivatedEvent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "eventType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserCreatedEvent.class, name = "UserCreated"),
        @JsonSubTypes.Type(value = UserEmailUpdatedEvent.class, name = "UserEmailUpdated"),
        @JsonSubTypes.Type(value = UserDeactivatedEvent.class, name = "UserDeactivated")
})
public interface DomainEventMixin {
}