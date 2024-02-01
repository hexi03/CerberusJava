package com.hexi.Cerberus.domain.repository.impl;

import com.hexi.Cerberus.domain.entities.WareHouse;
import com.hexi.Cerberus.domain.repository.WareHouseRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class WareHouseRepositoryImpl implements WareHouseRepository {

    private final JdbcTemplate jdbcTemplate;

    public WareHouseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public WareHouse getById(String id) {
        String sql = "SELECT * FROM warehouses WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(WareHouse.class));
    }

    @Override
    public List<WareHouse> getByDepartmentId(String id) {
        String sql = "SELECT * FROM warehouses WHERE department_id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(WareHouse.class));
    }

    @Override
    public List<WareHouse> getAll() {
        String sql = "SELECT * FROM warehouses";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WareHouse.class));
    }

    @Override
    public WareHouse append(WareHouse wh) {
        System.err.println(wh);
        String sql = "INSERT INTO warehouses (id, department_id, name) VALUES (?, ?, ?) RETURNING id";
        jdbcTemplate.queryForObject(sql, new Object[]{wh.getId().getId(), wh.getDepartmentId().getId(), wh.getName()}, String.class);
        return wh;
    }

    @Override
    public void update(WareHouse wh) {
        System.err.println(wh);
        String sql = "UPDATE warehouses SET name = ?, department_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, wh.getName(), wh.getDepartmentId().getId(), wh.getId().getId());
    }

    @Override
    public void deleteById(String id) {
        System.err.println(id);
        String sql = "DELETE FROM warehouses WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isExists(String id) {
        String sql = "SELECT COUNT(*) FROM warehouses WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}