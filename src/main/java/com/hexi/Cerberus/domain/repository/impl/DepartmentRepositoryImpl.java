package com.hexi.Cerberus.domain.repository.impl;

import com.hexi.Cerberus.domain.entities.Department;
import com.hexi.Cerberus.domain.repository.DepartmentRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Department getById(String id) {
        String sql = "SELECT * FROM departments WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Department.class));
    }

    @Override
    public List<Department> getAll() {
        String sql = "SELECT * FROM departments";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Department.class));
    }

    @Override
    public Department append(Department dep) {
        String sql = "INSERT INTO departments (id, name) VALUES (?, ?) RETURNING id";
        jdbcTemplate.queryForObject(sql, new Object[]{dep.getId().getId(), dep.getName()}, String.class);
        return dep;
    }

    @Override
    public void update(Department dep) {
        String sql = "UPDATE departments SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, dep.getName(), dep.getId().getId());
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM departments WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isExists(String id) {
        String sql = "SELECT COUNT(*) FROM departments WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
