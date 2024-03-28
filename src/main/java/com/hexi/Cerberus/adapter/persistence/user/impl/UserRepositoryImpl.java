package com.hexi.Cerberus.adapter.persistence.user.impl;

import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> displayById(UserID id) {
        return Optional.empty();
    }

    @Override
    public List<User> displayById(List<UserID> userIDS) {
        return null;
    }

    @Override
    public List<User> displayAll(Query query) {
        return null;
    }

    @Override
    public List<User> displayAll() {
        return null;
    }

    @Override
    public User append(User user) {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void deleteById(UserID id) {

    }

    @Override
    public boolean isExists(UserID id) {
        return false;
    }
}
