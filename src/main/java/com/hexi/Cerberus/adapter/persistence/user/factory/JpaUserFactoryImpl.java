package com.hexi.Cerberus.adapter.persistence.user.factory;

import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserFactory;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.command.CreateUserCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JpaUserFactoryImpl implements UserFactory {

    @Autowired
    PasswordEncoder passwordEncoder;

    public JpaUserFactoryImpl(PasswordEncoder encoder) {
        passwordEncoder = encoder;
    }

    public User from(CreateUserCmd cmd) {
        return new UserModel(new UserID(), cmd.getName(), cmd.getEmail(), passwordEncoder.encode(cmd.getPassword()));
    }

    public User from(String name, String email, String password) {
        return new UserModel(new UserID(), name, email, passwordEncoder.encode(password));
    }
}
