package com.hexi.Cerberus.adapter.persistence.group.impl;

import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.group.repository.GroupRepository;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public class GroupRepositoryImpl implements GroupRepository {
    @Override
    public Optional<Group> displayById(GroupID id) {
        return Optional.empty();
    }

    @Override
    public List<Group> displayById(List<GroupID> groupIDS) {
        return null;
    }

    @Override
    public List<Group> displayAll(Query query) {
        return null;
    }

    @Override
    public List<Group> displayAll() {
        return null;
    }

    @Override
    public Group append(Group user) {
        return null;
    }

    @Override
    public void update(Group user) {

    }

    @Override
    public void deleteById(GroupID id) {

    }

    @Override
    public boolean isExists(GroupID id) {
        return false;
    }
}
