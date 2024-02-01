package com.hexi.Cerberus.domain.repository.impl;

import com.hexi.Cerberus.domain.entities.Group;
import com.hexi.Cerberus.domain.repository.GroupRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {

    private final JdbcTemplate jdbcTemplate;

    public GroupRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group getById(String id) {
        String sql = "SELECT * FROM groups WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public List<Group> getAll() {
        String sql = "SELECT * FROM groups";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public Group append(Group group) {
        String sql = "INSERT INTO groups (id, name) VALUES (?, ?) RETURNING id";
        jdbcTemplate.queryForObject(sql, new Object[]{group.getId().getId(), group.getName()}, String.class);
        return group;
    }

    @Override
    public void update(Group group) {
        String sql = "UPDATE groups SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, group.getName(), group.getId());
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM groups WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isExists(String id) {
        String sql = "SELECT COUNT(*) FROM groups WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
