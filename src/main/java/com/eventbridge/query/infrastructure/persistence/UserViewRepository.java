package com.eventbridge.query.infrastructure.persistence;

import com.eventbridge.command.domain.model.UserStatus;
import com.eventbridge.query.infrastructure.model.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserViewRepository extends JpaRepository<UserView, String> {
    Optional<UserView> findByUsername(String username);
    List<UserView> findByStatus(UserStatus status);
    // 添加存在性检查方法
    boolean existsByUserId(String userId);

    // 添加安全保存方法（如果需要）
    @Transactional
    default UserView saveIfNotExists(UserView userView) {
        if (!existsById(userView.getUserId())) {
            return save(userView);
        }
        return null;
    }
}
