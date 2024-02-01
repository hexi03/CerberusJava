package com.hexi.Cerberus.domain.repository.impl;


import com.hexi.Cerberus.domain.entities.User;
import com.hexi.Cerberus.domain.repository.UserRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User append(User user) {
        System.err.println(user.getId().getId());
        System.err.println(user.getName());
        System.err.println(user.getEmail());
        System.err.println(user.getPasswordHash());
        String sql = "INSERT INTO users (id, name, email, phash) VALUES (?, ?, ?, ?) RETURNING id";
        jdbcTemplate.queryForObject(sql, new Object[] {user.getId().getId(), user.getName(), user.getEmail(), user.getPasswordHash()}, String.class);
        return user;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, phash = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPasswordHash(), user.getId());
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isExists(String id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
