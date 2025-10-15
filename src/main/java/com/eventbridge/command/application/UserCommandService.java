package com.eventbridge.command.application;
import com.eventbridge.command.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eventbridge.command.domain.model.User;
import com.eventbridge.command.infrastructure.persistence.UserRepository;
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
        System.out.println("👤 [COMMAND] 开始处理创建用户命令: " + command.getUsername());

        User user = User.create(command.getUsername(), command.getEmail());
        userRepository.save(user);

        System.out.println("💾 [COMMAND] 用户保存到数据库: " + user.getId());

        publishDomainEvents(user);

        System.out.println("🎯 [COMMAND] 创建用户完成，用户ID: " + user.getId());
        return user.getId();
    }

    public void handle(UpdateUserEmailCommand command) {
        System.out.println("📧 [COMMAND] 开始处理更新邮箱命令: " + command.getUserId());

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updateEmail(command.getNewEmail());
        userRepository.save(user);

        System.out.println("✏️ [COMMAND] 邮箱更新完成: " + command.getUserId());
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
        System.out.println("📦 [COMMAND] 发布用户领域事件，数量: " + user.getDomainEvents().size());
        user.getDomainEvents().forEach(eventPublisher::publish);
        user.clearEvents();
    }
}
