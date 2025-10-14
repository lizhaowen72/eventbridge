package com.eventbridge.command.application.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserCommand {

    private final String username;
    private final String email;

    public CreateUserCommand(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
