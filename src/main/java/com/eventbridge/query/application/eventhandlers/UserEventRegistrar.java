package com.eventbridge.query.application.eventhandlers;

import org.springframework.stereotype.Component;
import com.eventbridge.common.event.EventProcessorRegistry;
import com.eventbridge.command.domain.events.DomainEvent;
import com.eventbridge.command.domain.events.UserCreatedEvent;
import com.eventbridge.command.domain.events.UserEmailUpdatedEvent;
import com.eventbridge.command.domain.events.UserDeactivatedEvent;
import com.eventbridge.query.application.UserViewUpdater;

import javax.annotation.PostConstruct;

@Component
public class UserEventRegistrar {

    private final EventProcessorRegistry eventProcessorRegistry;
    private final UserViewUpdater userViewUpdater;

    public UserEventRegistrar(EventProcessorRegistry eventProcessorRegistry,
                              UserViewUpdater userViewUpdater) {
        this.eventProcessorRegistry = eventProcessorRegistry;
        this.userViewUpdater = userViewUpdater;
    }

    @PostConstruct
    public void registerEventProcessors() {
        eventProcessorRegistry.registerProcessor("UserCreated", this::handleUserCreated);
        eventProcessorRegistry.registerProcessor("UserEmailUpdated", this::handleUserEmailUpdated);
        eventProcessorRegistry.registerProcessor("UserDeactivated", this::handleUserDeactivated);

        System.out.println("User event processors registered successfully");
    }

    private void handleUserCreated(DomainEvent event) {
        UserCreatedEvent userCreatedEvent = (UserCreatedEvent) event;
        userViewUpdater.createUserView(
                userCreatedEvent.getUserId(),
                userCreatedEvent.getUsername(),
                userCreatedEvent.getEmail(),
                userCreatedEvent.getCreatedAt()
        );
    }

    private void handleUserEmailUpdated(DomainEvent event) {
        UserEmailUpdatedEvent emailUpdatedEvent = (UserEmailUpdatedEvent) event;
        userViewUpdater.updateUserEmail(
                emailUpdatedEvent.getUserId(),
                emailUpdatedEvent.getNewEmail()
        );
    }

    private void handleUserDeactivated(DomainEvent event) {
        UserDeactivatedEvent deactivatedEvent = (UserDeactivatedEvent) event;
        userViewUpdater.deactivateUser(deactivatedEvent.getUserId());
    }
}