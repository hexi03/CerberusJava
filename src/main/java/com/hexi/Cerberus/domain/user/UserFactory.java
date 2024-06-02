package com.hexi.Cerberus.domain.user;

import com.hexi.Cerberus.application.user.service.command.CreateUserCmd;
import org.springframework.stereotype.Component;

@Component
public interface UserFactory {

    User from(CreateUserCmd cmd);
    User from(String name, String email, String password);
}


