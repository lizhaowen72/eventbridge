package com.eventbridge.query.infrastructure.model;

import com.eventbridge.command.domain.model.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// query/infrastructure/model/UserView.java
@Entity
@Table(name = "user_views")
@Getter
@Setter
public class UserView {
    @Id
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime lastUpdated;

    // constructors, getters, setters
    public UserView() {}

    public UserView(String userId, String username, String email, LocalDateTime createdAt, UserStatus status) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.status = status;
        this.lastUpdated = LocalDateTime.now();
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
        this.lastUpdated = LocalDateTime.now();
    }

    public void deactivate() {
        this.status = UserStatus.INACTIVE;
        this.lastUpdated = LocalDateTime.now();
    }
}
