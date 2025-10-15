package com.eventbridge.command;

import com.eventbridge.command.application.UserCommandService;
import com.eventbridge.command.application.commands.CreateUserCommand;
import com.eventbridge.command.application.commands.UpdateUserEmailCommand;
import com.eventbridge.command.domain.model.UserStatus;
import com.eventbridge.command.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserCommandServiceTest {

    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateUser() {
        // 创建用户
        String userId = userCommandService.handle(
                new CreateUserCommand("johndoe", "john@example.com")
        );

        assertNotNull(userId);

        // 验证用户已保存到命令端数据库
        var userOpt = userRepository.findById(userId);
        assertTrue(userOpt.isPresent());
        assertEquals("johndoe", userOpt.get().getUsername());
        assertEquals("john@example.com", userOpt.get().getEmail());
    }

    @Test
    void testUpdateUserEmail() {
        // 先创建用户
        String userId = userCommandService.handle(
                new CreateUserCommand("janedoe", "old@example.com")
        );

        // 更新邮箱
        userCommandService.handle(
                new UpdateUserEmailCommand(userId, "new@example.com")
        );

        // 验证邮箱已更新
        var userOpt = userRepository.findById(userId);
        assertTrue(userOpt.isPresent());
        assertEquals("new@example.com", userOpt.get().getEmail());
    }

    @Test
    void testDeactivateUser() {
        // 先创建用户
        String userId = userCommandService.handle(
                new CreateUserCommand("inactiveuser", "inactive@example.com")
        );

        // 停用用户
        userCommandService.deactivateUser(userId);

        // 验证用户状态
        var userOpt = userRepository.findById(userId);
        assertTrue(userOpt.isPresent());
        assertEquals(UserStatus.INACTIVE, userOpt.get().getStatus());
    }
}