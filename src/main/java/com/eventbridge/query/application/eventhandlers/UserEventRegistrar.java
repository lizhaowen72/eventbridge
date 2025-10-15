package com.eventbridge.query.application.eventhandlers;

import com.eventbridge.common.enums.UserStatus;
import org.springframework.stereotype.Component;
import com.eventbridge.common.event.EventProcessorRegistry;
import com.eventbridge.command.domain.events.DomainEvent;
import com.eventbridge.command.domain.events.UserCreatedEvent;
import com.eventbridge.command.domain.events.UserEmailUpdatedEvent;
import com.eventbridge.command.domain.events.UserDeactivatedEvent;
import com.eventbridge.query.infrastructure.model.UserView;
import com.eventbridge.query.infrastructure.persistence.UserViewRepository;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
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
     * 处理用户创建事件
     */
    private void handleUserCreated(DomainEvent event) {
        UserCreatedEvent userCreatedEvent = (UserCreatedEvent) event;

        System.out.println("🔄 [EVENT-REGISTRY] 处理 UserCreatedEvent: " + userCreatedEvent.getUserId());
        System.out.println("   📝 事件详情 - 用户名: " + userCreatedEvent.getUsername() + ", 邮箱: " + userCreatedEvent.getEmail());

        try {
            // 幂等性检查
            if (userViewRepository.existsById(userCreatedEvent.getUserId())) {
                System.out.println("⏭️ [EVENT-REGISTRY] 用户视图已存在，跳过创建: " + userCreatedEvent.getUserId());
                return;
            }

            // 创建用户视图
            UserView userView = new UserView(
                    userCreatedEvent.getUserId(),
                    userCreatedEvent.getUsername(),
                    userCreatedEvent.getEmail(),
                    userCreatedEvent.getCreatedAt(),
                    UserStatus.ACTIVE
            );

            userViewRepository.save(userView);
            System.out.println("✅ [EVENT-REGISTRY] 用户视图创建成功: " + userCreatedEvent.getUsername());

        } catch (Exception e) {
            System.err.println("❌ [EVENT-REGISTRY] 处理 UserCreatedEvent 失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理用户邮箱更新事件
     */
    private void handleUserEmailUpdated(DomainEvent event) {
        UserEmailUpdatedEvent emailUpdatedEvent = (UserEmailUpdatedEvent) event;

        System.out.println("🔄 [EVENT-REGISTRY] 处理 UserEmailUpdatedEvent: " + emailUpdatedEvent.getUserId());
        System.out.println("   📝 事件详情 - 新邮箱: " + emailUpdatedEvent.getNewEmail());

        try {
            Optional<UserView> userOpt = userViewRepository.findById(emailUpdatedEvent.getUserId());
            if (userOpt.isPresent()) {
                UserView userView = userOpt.get();
                String oldEmail = userView.getEmail();
                userView.updateEmail(emailUpdatedEvent.getNewEmail());
                userViewRepository.save(userView);
                System.out.println("✅ [EVENT-REGISTRY] 用户邮箱更新成功: " + emailUpdatedEvent.getUserId());
                System.out.println("   📧 邮箱从 '" + oldEmail + "' 更新为 '" + emailUpdatedEvent.getNewEmail() + "'");
            } else {
                System.out.println("⚠️ [EVENT-REGISTRY] 用户视图未找到，无法更新邮箱: " + emailUpdatedEvent.getUserId());
                // 在实际生产环境中，这里可能需要从命令端重新同步数据
            }
        } catch (Exception e) {
            System.err.println("❌ [EVENT-REGISTRY] 处理 UserEmailUpdatedEvent 失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理用户停用事件
     */
    private void handleUserDeactivated(DomainEvent event) {
        UserDeactivatedEvent deactivatedEvent = (UserDeactivatedEvent) event;

        System.out.println("🔄 [EVENT-REGISTRY] 处理 UserDeactivatedEvent: " + deactivatedEvent.getUserId());

        try {
            Optional<UserView> userOpt = userViewRepository.findById(deactivatedEvent.getUserId());
            if (userOpt.isPresent()) {
                UserView userView = userOpt.get();
                UserStatus oldStatus = userView.getStatus();
                userView.deactivate();
                userViewRepository.save(userView);
                System.out.println("✅ [EVENT-REGISTRY] 用户停用成功: " + deactivatedEvent.getUserId());
                System.out.println("   🔄 状态从 " + oldStatus + " 更新为 " + userView.getStatus());
            } else {
                System.out.println("⚠️ [EVENT-REGISTRY] 用户视图未找到，无法停用: " + deactivatedEvent.getUserId());
            }
        } catch (Exception e) {
            System.err.println("❌ [EVENT-REGISTRY] 处理 UserDeactivatedEvent 失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取已注册的事件类型（用于诊断）
     */
    public void printRegisteredEventTypes() {
        System.out.println("=== 📊 已注册的用户事件类型 ===");
        // 这里假设 EventProcessorRegistry 有获取所有事件类型的方法
        // 如果没有，可以在 EventProcessorRegistry 中添加相应方法
        System.out.println("- UserCreated");
        System.out.println("- UserEmailUpdated");
        System.out.println("- UserDeactivated");
    }

    /**
     * 重新注册事件处理器（用于动态重新加载）
     */
    public void reRegisterProcessors() {
        System.out.println("🔄 重新注册用户事件处理器...");
        registerEventProcessors();
    }
}