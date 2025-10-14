package com.eventbridge.command.application;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eventbridge.command.domain.model.User;
import com.eventbridge.command.infrastructure.persistence.UserRepository;
import com.eventbridge.command.infrastructure.events.DomainEventPublisher;
import com.eventbridge.command.application.commands.CreateUserCommand;
import com.eventbridge.command.application.commands.UpdateUserEmailCommand;

@Service
@Transactional
public class UserCommandService {

    private final UserRepository userRepository;
    private final DomainEventPublisher eventPublisher;

    public UserCommandService(UserRepository userRepository, DomainEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public String handle(CreateUserCommand command) {
        User user = User.create(command.getUsername(), command.getEmail());
        userRepository.save(user);

        // 发布领域事件
        publishDomainEvents(user);

        return user.getId();
    }

    public void handle(UpdateUserEmailCommand command) {
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updateEmail(command.getNewEmail());
        userRepository.save(user);

        // 发布领域事件
        publishDomainEvents(user);
    }

    public void deactivateUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.deactivate();
        userRepository.save(user);

        // 发布领域事件
        publishDomainEvents(user);
    }

    private void publishDomainEvents(User user) {
        user.getDomainEvents().forEach(eventPublisher::publish);
        user.clearEvents();
    }
}
