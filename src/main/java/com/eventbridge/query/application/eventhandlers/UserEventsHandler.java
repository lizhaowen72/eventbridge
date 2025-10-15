package com.eventbridge.query.application.eventhandlers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.dao.DataIntegrityViolationException;
import com.eventbridge.command.domain.events.UserCreatedEvent;
import com.eventbridge.command.domain.events.UserEmailUpdatedEvent;
import com.eventbridge.command.domain.events.UserDeactivatedEvent;
import com.eventbridge.query.infrastructure.model.UserView;
import com.eventbridge.query.infrastructure.persistence.UserViewRepository;
import com.eventbridge.command.domain.model.UserStatus;
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
     * 使用 @TransactionalEventListener 确保在命令端事务提交后处理
     * 使用 @Async 异步处理，不阻塞命令端
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("taskExecutor")
    public void handleUserCreated(UserCreatedEvent event) {
        String userId = event.getUserId();
        System.out.println("🔄 [QUERY-LOCAL] 开始处理 UserCreatedEvent: " + userId);
        System.out.println("   📝 事件详情 - 用户名: " + event.getUsername() + ", 邮箱: " + event.getEmail());

        try {
            // 幂等性检查：如果用户视图已存在，则跳过创建
            if (userViewRepository.existsById(userId)) {
                System.out.println("⏭️ [QUERY-LOCAL] 用户视图已存在，跳过创建: " + userId);
                return;
            }

            // 创建用户视图
            UserView userView = new UserView(
                    userId,
                    event.getUsername(),
                    event.getEmail(),
                    event.getCreatedAt(),
                    UserStatus.ACTIVE
            );

            // 尝试保存，捕获可能的重复插入异常
            try {
                userViewRepository.save(userView);
                System.out.println("✅ [QUERY-LOCAL] 用户视图创建成功: " + event.getUsername() + " (ID: " + userId + ")");
            } catch (DataIntegrityViolationException e) {
                // 处理主键冲突异常（虽然我们做了存在性检查，但并发情况下仍可能发生）
                System.out.println("⚠️ [QUERY-LOCAL] 用户视图已存在（捕获异常）: " + userId);
                // 忽略重复插入异常，这是最终一致性中的正常情况
            } catch (Exception e) {
                System.err.println("❌ [QUERY-LOCAL] 保存用户视图失败: " + e.getMessage());
                throw e; // 重新抛出其他异常
            }

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
        String userId = event.getUserId();
        System.out.println("🔄 [QUERY-LOCAL] 开始处理 UserEmailUpdatedEvent: " + userId);
        System.out.println("   📝 事件详情 - 新邮箱: " + event.getNewEmail());

        try {
            Optional<UserView> userOpt = userViewRepository.findById(userId);
            if (userOpt.isPresent()) {
                UserView userView = userOpt.get();
                String oldEmail = userView.getEmail();

                // 检查邮箱是否真的需要更新（避免不必要的数据库操作）
                if (!event.getNewEmail().equals(oldEmail)) {
                    userView.updateEmail(event.getNewEmail());

                    try {
                        userViewRepository.save(userView);
                        System.out.println("✅ [QUERY-LOCAL] 用户邮箱更新成功: " + userId);
                        System.out.println("   📧 邮箱从 '" + oldEmail + "' 更新为 '" + event.getNewEmail() + "'");
                    } catch (Exception e) {
                        System.err.println("❌ [QUERY-LOCAL] 保存邮箱更新失败: " + e.getMessage());
                    }
                } else {
                    System.out.println("⏭️ [QUERY-LOCAL] 邮箱未变化，跳过更新: " + userId);
                }
            } else {
                System.out.println("⚠️ [QUERY-LOCAL] 用户视图未找到，无法更新邮箱: " + userId);
                // 在实际生产环境中，这里可能需要从命令端重新同步数据
                handleMissingUserView(userId, "邮箱更新");
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
        String userId = event.getUserId();
        System.out.println("🔄 [QUERY-LOCAL] 开始处理 UserDeactivatedEvent: " + userId);

        try {
            Optional<UserView> userOpt = userViewRepository.findById(userId);
            if (userOpt.isPresent()) {
                UserView userView = userOpt.get();
                UserStatus oldStatus = userView.getStatus();

                // 检查用户是否已经是停用状态
                if (oldStatus != UserStatus.INACTIVE) {
                    userView.deactivate();

                    try {
                        userViewRepository.save(userView);
                        System.out.println("✅ [QUERY-LOCAL] 用户停用成功: " + userId);
                        System.out.println("   🔄 状态从 " + oldStatus + " 更新为 " + userView.getStatus());
                    } catch (Exception e) {
                        System.err.println("❌ [QUERY-LOCAL] 保存停用状态失败: " + e.getMessage());
                    }
                } else {
                    System.out.println("⏭️ [QUERY-LOCAL] 用户已是停用状态，跳过操作: " + userId);
                }
            } else {
                System.out.println("⚠️ [QUERY-LOCAL] 用户视图未找到，无法停用: " + userId);
                handleMissingUserView(userId, "停用操作");
            }
        } catch (Exception e) {
            System.err.println("❌ [QUERY-LOCAL] 处理 UserDeactivatedEvent 失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理用户激活事件（如果需要）- 示例方法
     * 注意：需要先创建 UserActivatedEvent
     */
    /*
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("taskExecutor")
    public void handleUserActivated(UserActivatedEvent event) {
        String userId = event.getUserId();
        System.out.println("🔄 [QUERY-LOCAL] 开始处理用户激活事件: " + userId);

        try {
            Optional<UserView> userOpt = userViewRepository.findById(userId);
            if (userOpt.isPresent()) {
                UserView userView = userOpt.get();
                UserStatus oldStatus = userView.getStatus();

                if (oldStatus != UserStatus.ACTIVE) {
                    userView.setStatus(UserStatus.ACTIVE);
                    userView.setLastUpdated(LocalDateTime.now());

                    try {
                        userViewRepository.save(userView);
                        System.out.println("✅ [QUERY-LOCAL] 用户激活成功: " + userId);
                        System.out.println("   🔄 状态从 " + oldStatus + " 更新为 " + userView.getStatus());
                    } catch (Exception e) {
                        System.err.println("❌ [QUERY-LOCAL] 保存激活状态失败: " + e.getMessage());
                    }
                } else {
                    System.out.println("⏭️ [QUERY-LOCAL] 用户已是激活状态，跳过操作: " + userId);
                }
            } else {
                System.out.println("⚠️ [QUERY-LOCAL] 用户视图未找到，无法激活: " + userId);
                handleMissingUserView(userId, "激活操作");
            }
        } catch (Exception e) {
            System.err.println("❌ [QUERY-LOCAL] 处理用户激活事件失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    */

    /**
     * 处理用户视图缺失的情况
     */
    private void handleMissingUserView(String userId, String operation) {
        System.err.println("🚨 [QUERY-LOCAL] 严重: 无法执行 " + operation + "，用户视图不存在: " + userId);

        // 在实际生产环境中，这里可以：
        // 1. 记录错误到监控系统
        // 2. 触发数据重新同步
        // 3. 发送告警通知

        // 示例：记录到错误日志
        System.err.println("   可能需要从命令端重新同步用户数据");
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

    /**
     * 诊断方法：获取用户状态统计
     */
    public void printUserStatusStatistics() {
        long activeCount = userViewRepository.findByStatus(UserStatus.ACTIVE).size();
        long inactiveCount = userViewRepository.findByStatus(UserStatus.INACTIVE).size();
        long totalCount = userViewRepository.count();

        System.out.println("📊 用户状态统计:");
        System.out.println("   活跃用户: " + activeCount);
        System.out.println("   停用用户: " + inactiveCount);
        System.out.println("   总计: " + totalCount);
    }

    /**
     * 安全创建用户视图（带完整错误处理）
     */
    public boolean safeCreateUserView(UserCreatedEvent event) {
        try {
            if (userViewRepository.existsById(event.getUserId())) {
                System.out.println("⏭️ 用户视图已存在: " + event.getUserId());
                return false;
            }

            UserView userView = new UserView(
                    event.getUserId(),
                    event.getUsername(),
                    event.getEmail(),
                    event.getCreatedAt(),
                    UserStatus.ACTIVE
            );

            userViewRepository.save(userView);
            System.out.println("✅ 用户视图安全创建成功: " + event.getUsername());
            return true;

        } catch (DataIntegrityViolationException e) {
            System.out.println("⚠️ 用户视图已存在（安全创建）: " + event.getUserId());
            return false;
        } catch (Exception e) {
            System.err.println("❌ 安全创建用户视图失败: " + e.getMessage());
            return false;
        }
    }
}