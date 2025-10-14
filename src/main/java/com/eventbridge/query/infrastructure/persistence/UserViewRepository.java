package com.eventbridge.query.infrastructure.persistence;

import com.eventbridge.common.enums.UserStatus;
import com.eventbridge.query.infrastructure.model.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserViewRepository extends JpaRepository<UserView, String> {
    Optional<UserView> findByUsername(String username);
    List<UserView> findByStatus(UserStatus status);
}
