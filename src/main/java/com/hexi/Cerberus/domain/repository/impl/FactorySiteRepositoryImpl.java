package com.hexi.Cerberus.domain.repository.impl;

import com.hexi.Cerberus.domain.entities.FactorySite;
import com.hexi.Cerberus.domain.repository.FactorySiteRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class FactorySiteRepositoryImpl implements FactorySiteRepository {

    private final JdbcTemplate jdbcTemplate;

    public FactorySiteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public FactorySite getById(String id) {
        String sql = "SELECT * FROM factorysites WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(FactorySite.class));
    }

    @Override
    public List<FactorySite> getByDepartmentId(String id) {
        String sql = "SELECT * FROM factorysites WHERE department_id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(FactorySite.class));
    }

    @Override
    public List<FactorySite> getAll() {
        String sql = "SELECT * FROM factorysites";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(FactorySite.class));
    }

    @Override
    public FactorySite append(FactorySite fs) {
        System.err.println(fs);
        String sql = "INSERT INTO factorysites (id, department_id, name) VALUES (?, ?, ?) RETURNING id";
        jdbcTemplate.queryForObject(sql, new Object[]{fs.getId().getId(), fs.getDepartmentId().getId(), fs.getName()}, String.class);
        return fs;
    }

    @Override
    public void update(FactorySite fs) {
        System.err.println(fs);
        String sql = "UPDATE factorysites SET name = ?, department_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, fs.getName(), fs.getDepartmentId().getId(),  fs.getId().getId());
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM factorysites WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isExists(String id) {
        String sql = "SELECT COUNT(*) FROM factorysites WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
