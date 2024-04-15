package com.hexi.Cerberus.domain.user;

import com.hexi.Cerberus.domain.user.command.CreateUserCmd;
import org.springframework.stereotype.Component;

@Component
public interface UserFactory {
    User from(CreateUserCmd cmd);
}


