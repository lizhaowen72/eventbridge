package com.eventbridge.query.application;

import com.eventbridge.common.enums.UserStatus;
import com.eventbridge.query.infrastructure.model.UserView;
import com.eventbridge.query.infrastructure.persistence.UserViewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserQueryService {
    private final UserViewRepository userViewRepository;

    public UserQueryService(UserViewRepository userViewRepository) {
        this.userViewRepository = userViewRepository;
    }

    public List<UserView> getAllUsers() {
        return userViewRepository.findAll();
    }

    public Optional<UserView> getUserById(String userId) {
        return userViewRepository.findById(userId);
    }

    public Optional<UserView> getUserByUsername(String username) {
        return userViewRepository.findByUsername(username);
    }

    public List<UserView> getActiveUsers() {
        return userViewRepository.findByStatus(UserStatus.ACTIVE);
    }
}
