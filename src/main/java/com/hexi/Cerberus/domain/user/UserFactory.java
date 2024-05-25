package com.hexi.Cerberus.domain.user;

import com.hexi.Cerberus.domain.user.command.CreateUserCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public interface UserFactory {

    User from(CreateUserCmd cmd);
    User from(String name, String email, String password);
}


