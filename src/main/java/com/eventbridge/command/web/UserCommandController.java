package com.eventbridge.command.web;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eventbridge.command.application.UserCommandService;
import com.eventbridge.command.application.commands.CreateUserCommand;
import com.eventbridge.command.application.commands.UpdateUserEmailCommand;


@RestController
@RequestMapping("/api/command/users")
public class UserCommandController {

    private final UserCommandService userCommandService;

    public UserCommandController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping
    public ResponseEntity<UserCreatedResponse> createUser(@RequestBody CreateUserRequest request) {
        CreateUserCommand command = new CreateUserCommand(request.getUsername(), request.getEmail());
        String userId = userCommandService.handle(command);

        return ResponseEntity.ok(new UserCreatedResponse(userId, "User created successfully"));
    }

    @PutMapping("/{userId}/email")
    public ResponseEntity<Void> updateEmail(@PathVariable String userId,
                                            @RequestBody UpdateEmailRequest request) {
        UpdateUserEmailCommand command = new UpdateUserEmailCommand(userId, request.getNewEmail());
        userCommandService.handle(command);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable String userId) {
        userCommandService.deactivateUser(userId);
        return ResponseEntity.ok().build();
    }

    @Getter
    @Setter
    // DTOs
    public static class CreateUserRequest {
        private String username;
        private String email;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserCreatedResponse {
        private String userId;
        private String message;
    }

    @Getter
    @Setter
    public static class UpdateEmailRequest {
        private String newEmail;
    }
}
