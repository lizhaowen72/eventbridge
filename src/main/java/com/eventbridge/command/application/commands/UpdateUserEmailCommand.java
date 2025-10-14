package com.eventbridge.command.application.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserEmailCommand {
    private final String userId;
    private final String newEmail;

    public UpdateUserEmailCommand(String userId, String newEmail) {
        this.userId = userId;
        this.newEmail = newEmail;
    }
}
