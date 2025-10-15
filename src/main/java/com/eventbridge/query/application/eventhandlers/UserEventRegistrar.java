package com.eventbridge.query.application.eventhandlers;

import org.springframework.stereotype.Component;
import com.eventbridge.common.event.EventProcessorRegistry;
import com.eventbridge.command.domain.events.DomainEvent;
import com.eventbridge.command.domain.events.UserCreatedEvent;
import com.eventbridge.command.domain.events.UserEmailUpdatedEvent;
import com.eventbridge.command.domain.events.UserDeactivatedEvent;
import com.eventbridge.query.infrastructure.model.UserView;
import com.eventbridge.query.infrastructure.persistence.UserViewRepository;
import com.eventbridge.command.domain.model.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UserEventRegistrar {

    private final EventProcessorRegistry eventProcessorRegistry;
    private final UserViewRepository userViewRepository;

    @Autowired
    public UserEventRegistrar(EventProcessorRegistry eventProcessorRegistry,
                              UserViewRepository userViewRepository) {
        this.eventProcessorRegistry = eventProcessorRegistry;
        this.userViewRepository = userViewRepository;
    }

    /**
     * 在应用启动后注册所有用户相关的事件处理器
     * 这些处理器主要用于处理来自 RabbitMQ 的事件
     */
    @PostConstruct
    public void registerEventProcessors() {
        System.out.println("=== 🚀 开始注册用户事件处理器 ===");

        // 注册用户创建事件处理器
        eventProcessorRegistry.registerProcessor("UserCreated", this::handleUserCreated);
        System.out.println("✅ 注册 UserCreated 事件处理器");

        // 注册用户邮箱更新事件处理器
        eventProcessorRegistry.registerProcessor("UserEmailUpdated", this::handleUserEmailUpdated);
        System.out.println("✅ 注册 UserEmailUpdated 事件处理器");

        // 注册用户停用事件处理器
        eventProcessorRegistry.registerProcessor("UserDeactivated", this::handleUserDeactivated);
        System.out.println("✅ 注册 UserDeactivated 事件处理器");

        // 验证所有处理器都已正确注册
        validateRegistrations();

        System.out.println("=== 🎉 用户事件处理器注册完成 ===");
    }

    /**
     * 验证所有事件处理器是否成功注册
     */
    private void validateRegistrations() {
        System.out.println("--- 📋 事件处理器注册验证 ---");

        boolean userCreatedRegistered = eventProcessorRegistry.hasProcessor("UserCreated");
        boolean userEmailUpdatedRegistered = eventProcessorRegistry.hasProcessor("UserEmailUpdated");
        boolean userDeactivatedRegistered = eventProcessorRegistry.hasProcessor("UserDeactivated");

        System.out.println("UserCreated 处理器: " + (userCreatedRegistered ? "✅ 已注册" : "❌ 未注册"));
        System.out.println("UserEmailUpdated 处理器: " + (userEmailUpdatedRegistered ? "✅ 已注册" : "❌ 未注册"));
        System.out.println("UserDeactivated 处理器: " + (userDeactivatedRegistered ? "✅ 已注册" : "❌ 未注册"));
        System.out.println("总注册处理器数量: " + eventProcessorRegistry.getProcessorCount());

        if (userCreatedRegistered && userEmailUpdatedRegistered && userDeactivatedRegistered) {
            System.out.println("🎯 所有用户事件处理器注册成功！");
        } else {
            System.err.println("⚠️ 部分事件处理器注册失败！");
        }
    }

    /**
     * 处理用户创建事件 - 具有幂等性
     */
    private void handleUserCreated(DomainEvent event) {
        UserCreatedEvent userCreatedEvent = (UserCreatedEvent) event;
        String userId = userCreatedEvent.getUserId();

        System.out.println("🔄 [EVENT-REGISTRY] 处理 UserCreatedEvent: " + userId);
        System.out.println("   📝 事件详情 - 用户名: " + userCreatedEvent.getUsername() + ", 邮箱: " + userCreatedEvent.getEmail());

        try {
            // 幂等性检查：如果用户视图已存在，则跳过创建
            if (userViewRepository.existsById(userId)) {
                System.out.println("⏭️ [EVENT-REGISTRY] 用户视图已存在，跳过创建: " + userId);
                return;
            }

            // 创建用户视图
            UserView userView = new UserView(
                    userId,
                    userCreatedEvent.getUsername(),
                    userCreatedEvent.getEmail(),
                    userCreatedEvent.getCreatedAt(),
                    UserStatus.ACTIVE
            );

            // 尝试保存，捕获可能的重复插入异常
            try {
                userViewRepository.save(userView);
                System.out.println("✅ [EVENT-REGISTRY] 用户视图创建成功: " + userCreatedEvent.getUsername() + " (ID: " + userId + ")");
            } catch (DataIntegrityViolationException e) {
                // 处理主键冲突异常
                System.out.println("⚠️ [EVENT-REGISTRY] 用户视图已存在（捕获异常）: " + userId);
                // 忽略重复插入异常
            } catch (Exception e) {
                System.err.println("❌ [EVENT-REGISTRY] 保存用户视图失败: " + e.getMessage());
                throw e; // 重新抛出其他异常
            }

        } catch (Exception e) {
            System.err.println("❌ [EVENT-REGISTRY] 处理 UserCreatedEvent 失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理用户邮箱更新事件 - 具有幂等性
     */
    private void handleUserEmailUpdated(DomainEvent event) {
        UserEmailUpdatedEvent emailUpdatedEvent = (UserEmailUpdatedEvent) event;
        String userId = emailUpdatedEvent.getUserId();

        System.out.println("🔄 [EVENT-REGISTRY] 处理 UserEmailUpdatedEvent: " + userId);
        System.out.println("   📝 事件详情 - 新邮箱: " + emailUpdatedEvent.getNewEmail());

        try {
            Optional<UserView> userOpt = userViewRepository.findById(userId);
            if (userOpt.isPresent()) {
                UserView userView = userOpt.get();
                String oldEmail = userView.getEmail();

                // 检查邮箱是否真的需要更新
                if (!emailUpdatedEvent.getNewEmail().equals(oldEmail)) {
                    userView.updateEmail(emailUpdatedEvent.getNewEmail());

                    try {
                        userViewRepository.save(userView);
                        System.out.println("✅ [EVENT-REGISTRY] 用户邮箱更新成功: " + userId);
                        System.out.println("   📧 邮箱从 '" + oldEmail + "' 更新为 '" + emailUpdatedEvent.getNewEmail() + "'");
                    } catch (Exception e) {
                        System.err.println("❌ [EVENT-REGISTRY] 保存邮箱更新失败: " + e.getMessage());
                    }
                } else {
                    System.out.println("⏭️ [EVENT-REGISTRY] 邮箱未变化，跳过更新: " + userId);
                }
            } else {
                System.out.println("⚠️ [EVENT-REGISTRY] 用户视图未找到，无法更新邮箱: " + userId);
                handleMissingUserView(userId, "邮箱更新");
            }
        } catch (Exception e) {
            System.err.println("❌ [EVENT-REGISTRY] 处理 UserEmailUpdatedEvent 失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理用户停用事件 - 具有幂等性
     */
    private void handleUserDeactivated(DomainEvent event) {
        UserDeactivatedEvent deactivatedEvent = (UserDeactivatedEvent) event;
        String userId = deactivatedEvent.getUserId();

        System.out.println("🔄 [EVENT-REGISTRY] 处理 UserDeactivatedEvent: " + userId);

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
                        System.out.println("✅ [EVENT-REGISTRY] 用户停用成功: " + userId);
                        System.out.println("   🔄 状态从 " + oldStatus + " 更新为 " + userView.getStatus());
                    } catch (Exception e) {
                        System.err.println("❌ [EVENT-REGISTRY] 保存停用状态失败: " + e.getMessage());
                    }
                } else {
                    System.out.println("⏭️ [EVENT-REGISTRY] 用户已是停用状态，跳过操作: " + userId);
                }
            } else {
                System.out.println("⚠️ [EVENT-REGISTRY] 用户视图未找到，无法停用: " + userId);
                handleMissingUserView(userId, "停用操作");
            }
        } catch (Exception e) {
            System.err.println("❌ [EVENT-REGISTRY] 处理 UserDeactivatedEvent 失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理用户视图缺失的情况
     */
    private void handleMissingUserView(String userId, String operation) {
        System.err.println("🚨 [EVENT-REGISTRY] 严重: 无法执行 " + operation + "，用户视图不存在: " + userId);

        // 在实际生产环境中，这里可以：
        // 1. 记录错误到监控系统
        // 2. 触发数据重新同步
        // 3. 发送告警通知

        System.err.println("   可能需要从命令端重新同步用户数据");

        // 可以在这里添加重新同步的逻辑
        // 例如：通过命令端 API 获取用户数据并创建视图
    }

    /**
     * 获取已注册的事件类型（用于诊断）
     */
    public void printRegisteredEventTypes() {
        System.out.println("=== 📊 UserEventRegistrar 已注册的事件类型 ===");
        System.out.println("- UserCreated");
        System.out.println("- UserEmailUpdated");
        System.out.println("- UserDeactivated");
        System.out.println("===========================================");
    }

    /**
     * 重新注册事件处理器（用于动态重新加载）
     */
    public void reRegisterProcessors() {
        System.out.println("🔄 重新注册用户事件处理器...");

        // 先注销所有现有的处理器
        eventProcessorRegistry.unregisterProcessor("UserCreated");
        eventProcessorRegistry.unregisterProcessor("UserEmailUpdated");
        eventProcessorRegistry.unregisterProcessor("UserDeactivated");

        // 重新注册
        registerEventProcessors();

        System.out.println("✅ 用户事件处理器重新注册完成");
    }

    /**
     * 检查特定事件类型的处理器是否存在
     */
    public boolean isProcessorRegistered(String eventType) {
        return eventProcessorRegistry.hasProcessor(eventType);
    }

    /**
     * 注销特定事件类型的处理器
     */
    public void unregisterProcessor(String eventType) {
        eventProcessorRegistry.unregisterProcessor(eventType);
        System.out.println("🗑️ 注销事件处理器: " + eventType);
    }

    /**
     * 获取处理器统计信息
     */
    public void printProcessorStatistics() {
        System.out.println("=== 📈 UserEventRegistrar 处理器统计 ===");
        System.out.println("总处理器数量: " + eventProcessorRegistry.getProcessorCount());
        System.out.println("UserCreated 处理器: " + (isProcessorRegistered("UserCreated") ? "✅ 已注册" : "❌ 未注册"));
        System.out.println("UserEmailUpdated 处理器: " + (isProcessorRegistered("UserEmailUpdated") ? "✅ 已注册" : "❌ 未注册"));
        System.out.println("UserDeactivated 处理器: " + (isProcessorRegistered("UserDeactivated") ? "✅ 已注册" : "❌ 未注册"));
    }

    /**
     * 安全处理用户创建事件（带完整错误处理）
     */
    public boolean safeHandleUserCreated(UserCreatedEvent event) {
        try {
            String userId = event.getUserId();

            // 幂等性检查
            if (userViewRepository.existsById(userId)) {
                System.out.println("⏭️ [EVENT-REGISTRY] 用户视图已存在（安全处理）: " + userId);
                return false;
            }

            UserView userView = new UserView(
                    userId,
                    event.getUsername(),
                    event.getEmail(),
                    event.getCreatedAt(),
                    UserStatus.ACTIVE
            );

            userViewRepository.save(userView);
            System.out.println("✅ [EVENT-REGISTRY] 用户视图安全创建成功: " + event.getUsername());
            return true;

        } catch (DataIntegrityViolationException e) {
            System.out.println("⚠️ [EVENT-REGISTRY] 用户视图已存在（安全处理捕获异常）: " + event.getUserId());
            return false;
        } catch (Exception e) {
            System.err.println("❌ [EVENT-REGISTRY] 安全处理用户创建事件失败: " + e.getMessage());
            return false;
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