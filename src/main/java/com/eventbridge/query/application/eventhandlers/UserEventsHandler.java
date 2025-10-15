package com.eventbridge.query.application.eventhandlers;

import com.eventbridge.common.enums.UserStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import com.eventbridge.command.domain.events.UserCreatedEvent;
import com.eventbridge.command.domain.events.UserEmailUpdatedEvent;
import com.eventbridge.command.domain.events.UserDeactivatedEvent;
import com.eventbridge.query.infrastructure.model.UserView;
import com.eventbridge.query.infrastructure.persistence.UserViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserEventsHandler {

    private final UserViewRepository userViewRepository;

    @Autowired
    public UserEventsHandler(UserViewRepository userViewRepository) {
        this.userViewRepository = userViewRepository;
    }

    /**
     * 处理用户创建事件 - 本地事务事件监听器
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("taskExecutor")
    public void handleUserCreated(UserCreatedEvent event) {
        System.out.println("🔄 [QUERY-LOCAL] 开始处理 UserCreatedEvent: " + event.getUserId());
        System.out.println("   📝 事件详情 - 用户名: " + event.getUsername() + ", 邮箱: " + event.getEmail());

        try {
            // 幂等性检查：如果用户视图已存在，则跳过创建
            if (userViewRepository.existsById(event.getUserId())) {
                System.out.println("⏭️ [QUERY-LOCAL] 用户视图已存在，跳过创建: " + event.getUserId());
                return;
            }

            // 创建用户视图
            UserView userView = new UserView(
                    event.getUserId(),
                    event.getUsername(),
                    event.getEmail(),
                    event.getCreatedAt(),
                    UserStatus.ACTIVE
            );

            userViewRepository.save(userView);
            System.out.println("✅ [QUERY-LOCAL] 用户视图创建成功: " + event.getUsername() + " (ID: " + event.getUserId() + ")");

        } catch (Exception e) {
            System.err.println("❌ [QUERY-LOCAL] 处理 UserCreatedEvent 失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理用户邮箱更新事件 - 本地事务事件监听器
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("taskExecutor")
    public void handleUserEmailUpdated(UserEmailUpdatedEvent event) {
        System.out.println("🔄 [QUERY-LOCAL] 开始处理 UserEmailUpdatedEvent: " + event.getUserId());
        System.out.println("   📝 事件详情 - 新邮箱: " + event.getNewEmail());

        try {
            Optional<UserView> userOpt = userViewRepository.findById(event.getUserId());
            if (userOpt.isPresent()) {
                UserView userView = userOpt.get();
                String oldEmail = userView.getEmail();
                userView.updateEmail(event.getNewEmail());
                userViewRepository.save(userView);
                System.out.println("✅ [QUERY-LOCAL] 用户邮箱更新成功: " + event.getUserId());
                System.out.println("   📧 邮箱从 '" + oldEmail + "' 更新为 '" + event.getNewEmail() + "'");
            } else {
                System.out.println("⚠️ [QUERY-LOCAL] 用户视图未找到，无法更新邮箱: " + event.getUserId());
                // 在实际生产环境中，这里可能需要从命令端重新同步数据
            }
        } catch (Exception e) {
            System.err.println("❌ [QUERY-LOCAL] 处理 UserEmailUpdatedEvent 失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理用户停用事件 - 本地事务事件监听器
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("taskExecutor")
    public void handleUserDeactivated(UserDeactivatedEvent event) {
        System.out.println("🔄 [QUERY-LOCAL] 开始处理 UserDeactivatedEvent: " + event.getUserId());

        try {
            Optional<UserView> userOpt = userViewRepository.findById(event.getUserId());
            if (userOpt.isPresent()) {
                UserView userView = userOpt.get();
                UserStatus oldStatus = userView.getStatus();
                userView.deactivate();
                userViewRepository.save(userView);
                System.out.println("✅ [QUERY-LOCAL] 用户停用成功: " + event.getUserId());
                System.out.println("   🔄 状态从 " + oldStatus + " 更新为 " + userView.getStatus());
            } else {
                System.out.println("⚠️ [QUERY-LOCAL] 用户视图未找到，无法停用: " + event.getUserId());
            }
        } catch (Exception e) {
            System.err.println("❌ [QUERY-LOCAL] 处理 UserDeactivatedEvent 失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理用户激活事件（如果需要的话）- 示例方法
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("taskExecutor")
    public void handleUserActivated(UserDeactivatedEvent event) {
        // 注意：这里使用 UserDeactivatedEvent 作为示例，实际应该有 UserActivatedEvent
        System.out.println("🔄 [QUERY-LOCAL] 开始处理用户激活事件: " + event.getUserId());

        try {
            Optional<UserView> userOpt = userViewRepository.findById(event.getUserId());
            if (userOpt.isPresent()) {
                UserView userView = userOpt.get();
                userView.setStatus(UserStatus.ACTIVE);
                userView.setLastUpdated(LocalDateTime.now());
                userViewRepository.save(userView);
                System.out.println("✅ [QUERY-LOCAL] 用户激活成功: " + event.getUserId());
            } else {
                System.out.println("⚠️ [QUERY-LOCAL] 用户视图未找到，无法激活: " + event.getUserId());
            }
        } catch (Exception e) {
            System.err.println("❌ [QUERY-LOCAL] 处理用户激活事件失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 诊断方法：检查用户视图是否存在
     */
    public boolean doesUserViewExist(String userId) {
        return userViewRepository.existsById(userId);
    }

    /**
     * 诊断方法：获取用户视图数量
     */
    public long getUserViewCount() {
        return userViewRepository.count();
    }
}