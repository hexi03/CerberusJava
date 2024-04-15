package com.hexi.Cerberus.domain.user;

import com.hexi.Cerberus.domain.user.command.UpdateUserDetailsCmd;
import org.springframework.stereotype.Component;

@Component
public class UserUpdater {
    public void updateBy(User user, UpdateUserDetailsCmd cmd) {
        user.setName(cmd.getName());
        user.setEmail(cmd.getEmail());
        user.setPasswordHash(cmd.getPasswordHash());
    }
}
