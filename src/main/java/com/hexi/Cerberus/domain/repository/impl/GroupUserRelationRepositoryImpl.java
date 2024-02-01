package com.hexi.Cerberus.domain.repository.impl;

import com.hexi.Cerberus.domain.entities.GroupUserRelation;
import com.hexi.Cerberus.domain.repository.GroupUserRelationsRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class GroupUserRelationRepositoryImpl implements GroupUserRelationsRepository {

    private final JdbcTemplate jdbcTemplate;

    public GroupUserRelationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GroupUserRelation getById(String id) {
        String sql = "SELECT * FROM group_user_relation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(GroupUserRelation.class));
    }

    @Override
    public List<GroupUserRelation> getAll() {
        String sql = "SELECT * FROM group_user_relation";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GroupUserRelation.class));
    }

    @Override
    public GroupUserRelation append(GroupUserRelation groupUserRelation) {
        String sql = "INSERT INTO group_user_relation (id, user_id, group_id) VALUES (?, ?, ?) RETURNING id";
        jdbcTemplate.queryForObject(sql, new Object[]{ groupUserRelation.getId().getId(), groupUserRelation.getUserId().getId(), groupUserRelation.getGroupId().getId()}, String.class);
        return groupUserRelation;
    }

    @Override
    public void update(GroupUserRelation groupUserRelation) {
        String sql = "UPDATE group_user_relation SET user_id = ?, group_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, groupUserRelation.getUserId(), groupUserRelation.getGroupId(), groupUserRelation.getId());
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM group_user_relation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isExists(String id) {
        String sql = "SELECT COUNT(*) FROM group_user_relation WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public List<GroupUserRelation> append(List<GroupUserRelation> groupUserRelations) {
        String sql = "INSERT INTO group_user_relation (id, user_id, group_id) VALUES (?, ?, ?)";
        groupUserRelations.forEach(relation -> jdbcTemplate.update(sql, relation.getId(), relation.getUserId(), relation.getGroupId()));
        return groupUserRelations;
    }
}
