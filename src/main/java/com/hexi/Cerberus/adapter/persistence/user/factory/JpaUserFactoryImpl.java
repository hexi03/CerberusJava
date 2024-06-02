package com.hexi.Cerberus.adapter.persistence.user.factory;

import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserFactory;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.application.user.service.command.CreateUserCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JpaUserFactoryImpl implements UserFactory {

    @Autowired
    PasswordEncoder passwordEncoder;

    public JpaUserFactoryImpl(PasswordEncoder encoder) {
        passwordEncoder = encoder;
    }

    public User from(CreateUserCmd cmd) {
        String passwordEncoded = passwordEncoder.encode(cmd.getPassword());
        System.out.println(String.format("Создаем юзера: \nИмя: %s\nПочта: %s\nПароль: %s\nХеш пароля: %s\n", cmd.getName(), cmd.getEmail(), cmd.getPassword(), passwordEncoded));
        return new UserModel(new UserID(), cmd.getName(), cmd.getEmail(), passwordEncoded);
    }

    public User from(String name, String email, String password) {
        String passwordEncoded = passwordEncoder.encode(password);
        System.out.println(String.format("Создаем юзера: \nИмя: %s\nПочта: %s\nПароль: %s\nХеш пароля: %s\n", name, email, password, passwordEncoded));

        return new UserModel(new UserID(), name, email, passwordEncoded);
    }
}
