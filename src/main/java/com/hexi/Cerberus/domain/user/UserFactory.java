package com.hexi.Cerberus.domain.user;

import com.hexi.Cerberus.domain.user.command.CreateUserCmd;

public class UserFactory {
    public User from(CreateUserCmd cmd) {
        return new User(new UserID(), cmd.getName(), cmd.getEmail(), cmd.getPasswordHash());
    }
}
