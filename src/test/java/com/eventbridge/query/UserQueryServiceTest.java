package com.eventbridge.query;

import com.eventbridge.command.application.UserCommandService;
import com.eventbridge.command.application.commands.CreateUserCommand;
import com.eventbridge.command.domain.model.UserStatus;
import com.eventbridge.query.application.UserQueryService;
import com.eventbridge.query.infrastructure.model.UserView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserQueryServiceTest {

    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private UserQueryService userQueryService;

    @Test
    void testGetUserById() {
        // 创建用户
        String userId = userCommandService.handle(
                new CreateUserCommand("queryuser", "query@example.com")
        );

        // 等待查询端数据同步
        await().atMost(10, TimeUnit.SECONDS).until(() ->
                userQueryService.getUserById(userId).isPresent()
        );

        // 验证查询
        Optional<UserView> userOpt = userQueryService.getUserById(userId);
        assertTrue(userOpt.isPresent());
        assertEquals("queryuser", userOpt.get().getUsername());
    }

    @Test
    void testGetUserByUsername() {
        // 创建用户
        String userId = userCommandService.handle(
                new CreateUserCommand("uniqueuser", "unique@example.com")
        );

        // 等待查询端数据同步
        await().atMost(10, TimeUnit.SECONDS).until(() ->
                userQueryService.getUserByUsername("uniqueuser").isPresent()
        );

        // 验证按用户名查询
        Optional<UserView> userOpt = userQueryService.getUserByUsername("uniqueuser");
        assertTrue(userOpt.isPresent());
        assertEquals("unique@example.com", userOpt.get().getEmail());
    }

    @Test
    void testGetActiveUsers() {
        // 创建活跃用户
        userCommandService.handle(new CreateUserCommand("active1", "active1@example.com"));
        userCommandService.handle(new CreateUserCommand("active2", "active2@example.com"));

        // 等待查询端数据同步
        await().atMost(10, TimeUnit.SECONDS).until(() ->
                userQueryService.getActiveUsers().size() >= 2
        );

        // 验证活跃用户查询
        List<UserView> activeUsers = userQueryService.getActiveUsers();
        assertTrue(activeUsers.size() >= 2);

        // 所有返回的用户都应该是活跃状态
        for (UserView user : activeUsers) {
            assertEquals(UserStatus.ACTIVE, user.getStatus());
        }
    }
}