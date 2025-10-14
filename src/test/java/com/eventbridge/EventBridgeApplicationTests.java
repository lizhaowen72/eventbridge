package com.eventbridge;

import com.eventbridge.command.application.UserCommandService;
import com.eventbridge.command.application.commands.CreateUserCommand;
import com.eventbridge.query.application.UserQueryService;
import com.eventbridge.query.infrastructure.model.UserView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class EventBridgeApplicationTests {

    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private UserQueryService userQueryService;

    @Test
    void contextLoads() {
        // 测试应用上下文是否正常加载
        assertNotNull(userCommandService);
        assertNotNull(userQueryService);
    }

    @Test
    void testUserCreationAndQuery() {
        // 创建用户
        String userId = userCommandService.handle(
                new CreateUserCommand("testuser", "test@example.com")
        );

        assertNotNull(userId);
        assertFalse(userId.isEmpty());

        // 等待事件处理完成（最终一致性）
        await().atMost(10, TimeUnit.SECONDS).until(() ->
                userQueryService.getUserById(userId).isPresent()
        );

        // 验证查询端数据
        Optional<UserView> userViewOpt = userQueryService.getUserById(userId);
        assertTrue(userViewOpt.isPresent());

        UserView userView = userViewOpt.get();
        assertEquals("testuser", userView.getUsername());
        assertEquals("test@example.com", userView.getEmail());
        assertEquals(userId, userView.getUserId());
    }

    @Test
    void testGetAllUsers() {
        // 创建测试用户
        String userId1 = userCommandService.handle(
                new CreateUserCommand("user1", "user1@example.com")
        );

        String userId2 = userCommandService.handle(
                new CreateUserCommand("user2", "user2@example.com")
        );

        // 等待事件处理
        await().atMost(10, TimeUnit.SECONDS).until(() ->
                userQueryService.getAllUsers().size() >= 2
        );

        // 验证可以获取所有用户
        var users = userQueryService.getAllUsers();
        assertTrue(users.size() >= 2);
    }
}