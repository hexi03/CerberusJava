package com.hexi.Cerberus.domain.user;

import com.hexi.Cerberus.application.user.service.command.UpdateUserDetailsCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserUpdater {
    @Autowired
    PasswordEncoder encoder;
    public void updateBy(User user, UpdateUserDetailsCmd cmd) {
        user.setName(cmd.getName());
        user.setEmail(cmd.getEmail());
        user.setPasswordHash(encoder.encode(cmd.getPassword()));
    }
}
