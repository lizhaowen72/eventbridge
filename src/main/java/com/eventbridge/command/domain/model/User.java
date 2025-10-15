package com.eventbridge.command.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// 如果使用 Spring Boot 3.x 和 Jakarta EE 9+
// import jakarta.persistence.*;

// 领域事件导入
import com.eventbridge.command.domain.events.DomainEvent;
import com.eventbridge.command.domain.events.UserCreatedEvent;
import com.eventbridge.command.domain.events.UserEmailUpdatedEvent;
import com.eventbridge.command.domain.events.UserDeactivatedEvent;
import jakarta.persistence.*;
import lombok.Getter;

// command/domain/model/User.java
@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    // 领域行为
    public static User create(String username, String email) {
        User user = new User();
        user.id = UUID.randomUUID().toString();
        user.username = username;
        user.email = email;
        user.createdAt = LocalDateTime.now();
        user.status = UserStatus.ACTIVE;

        // 发布领域事件
        user.registerEvent(new UserCreatedEvent(user.id, user.username, user.email, user.createdAt));
        return user;
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
        registerEvent(new UserEmailUpdatedEvent(this.id, this.email));
    }

    public void deactivate() {
        this.status = UserStatus.INACTIVE;
        registerEvent(new UserDeactivatedEvent(this.id));
    }

    // 事件相关
    @Transient
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    private void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    public void clearEvents() {
        domainEvents.clear();
    }

    // constructors, getters, setters
    public User() {}

    // getters...
}







