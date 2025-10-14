package com.eventbridge.query.application.eventhandlers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import com.eventbridge.command.domain.events.UserCreatedEvent;
import com.eventbridge.command.domain.events.UserEmailUpdatedEvent;
import com.eventbridge.command.domain.events.UserDeactivatedEvent;
import com.eventbridge.query.application.UserViewUpdater;

@Service
@Transactional
public class UserEventsHandler {

    private final UserViewUpdater userViewUpdater;

    public UserEventsHandler(UserViewUpdater userViewUpdater) {
        this.userViewUpdater = userViewUpdater;
    }

    // 处理本地应用事件（事务同步）
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("taskExecutor")
    public void handleUserCreated(UserCreatedEvent event) {
        System.out.println("Processing UserCreatedEvent for user: " + event.getUserId());
        userViewUpdater.createUserView(
                event.getUserId(),
                event.getUsername(),
                event.getEmail(),
                event.getCreatedAt()
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("taskExecutor")
    public void handleUserEmailUpdated(UserEmailUpdatedEvent event) {
        System.out.println("Processing UserEmailUpdatedEvent for user: " + event.getUserId());
        userViewUpdater.updateUserEmail(
                event.getUserId(),
                event.getNewEmail()
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("taskExecutor")
    public void handleUserDeactivated(UserDeactivatedEvent event) {
        System.out.println("Processing UserDeactivatedEvent for user: " + event.getUserId());
        userViewUpdater.deactivateUser(event.getUserId());
    }
}