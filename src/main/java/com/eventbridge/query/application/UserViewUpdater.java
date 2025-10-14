package com.eventbridge.query.application;


import com.eventbridge.common.enums.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eventbridge.query.infrastructure.model.UserView;
import com.eventbridge.query.infrastructure.persistence.UserViewRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserViewUpdater {

    private final UserViewRepository userViewRepository;

    public UserViewUpdater(UserViewRepository userViewRepository) {
        this.userViewRepository = userViewRepository;
    }

    /**
     * 创建用户视图
     */
    public void createUserView(String userId, String username, String email, LocalDateTime createdAt) {
        // 幂等性检查：如果用户视图已存在，则跳过创建
        if (userViewRepository.existsById(userId)) {
            System.out.println("User view already exists, skipping creation for user: " + userId);
            return;
        }

        UserView userView = new UserView(userId, username, email, createdAt, UserStatus.ACTIVE);
        userViewRepository.save(userView);
        System.out.println("User view created for: " + username);
    }

    /**
     * 更新用户邮箱
     */
    public void updateUserEmail(String userId, String newEmail) {
        Optional<UserView> userViewOpt = userViewRepository.findById(userId);
        if (userViewOpt.isPresent()) {
            UserView userView = userViewOpt.get();
            userView.updateEmail(newEmail);
            userViewRepository.save(userView);
            System.out.println("User email updated for: " + userId);
        } else {
            System.out.println("User view not found for email update: " + userId);
            // 在实际应用中，这里可能需要从命令端重新同步数据
            // 或者记录错误以便后续处理
        }
    }

    /**
     * 停用用户
     */
    public void deactivateUser(String userId) {
        Optional<UserView> userViewOpt = userViewRepository.findById(userId);
        if (userViewOpt.isPresent()) {
            UserView userView = userViewOpt.get();
            userView.deactivate();
            userViewRepository.save(userView);
            System.out.println("User deactivated: " + userId);
        } else {
            System.out.println("User view not found for deactivation: " + userId);
        }
    }

    /**
     * 更新用户名
     */
    public void updateUsername(String userId, String newUsername) {
        Optional<UserView> userViewOpt = userViewRepository.findById(userId);
        if (userViewOpt.isPresent()) {
            UserView userView = userViewOpt.get();
            userView.setUsername(newUsername);
            userView.setLastUpdated(LocalDateTime.now());
            userViewRepository.save(userView);
            System.out.println("Username updated for: " + userId);
        } else {
            System.out.println("User view not found for username update: " + userId);
        }
    }

    /**
     * 检查用户视图是否存在
     */
    public boolean userViewExists(String userId) {
        return userViewRepository.existsById(userId);
    }

    /**
     * 获取用户视图
     */
    public Optional<UserView> getUserView(String userId) {
        return userViewRepository.findById(userId);
    }

    /**
     * 删除用户视图（用于测试或数据清理）
     */
    public void deleteUserView(String userId) {
        userViewRepository.deleteById(userId);
        System.out.println("User view deleted: " + userId);
    }
}
