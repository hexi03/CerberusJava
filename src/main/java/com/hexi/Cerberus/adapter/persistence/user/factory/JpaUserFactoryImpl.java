package com.hexi.Cerberus.adapter.persistence.user.factory;

import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserFactory;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.command.CreateUserCmd;

public class JpaUserFactoryImpl implements UserFactory {
    public User from(CreateUserCmd cmd) {
        return new UserModel(new UserID(), cmd.getName(), cmd.getEmail(), cmd.getPasswordHash());
    }

    public User from(String name, String email, String passwordHash) {
        return new UserModel(new UserID(), name, email, passwordHash);
    }
}
