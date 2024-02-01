package com.hexi.Cerberus.domain.services.impl;

import com.hexi.Cerberus.domain.commontypes.GroupID;
import com.hexi.Cerberus.domain.commontypes.UserGroupID;
import com.hexi.Cerberus.domain.commontypes.UserID;
import com.hexi.Cerberus.domain.aggregates.UserGroups;
import com.hexi.Cerberus.domain.entities.Group;
import com.hexi.Cerberus.domain.entities.GroupUserRelation;
import com.hexi.Cerberus.domain.entities.User;
import com.hexi.Cerberus.domain.repository.GroupRepository;
import com.hexi.Cerberus.domain.repository.GroupUserRelationsRepository;
import com.hexi.Cerberus.domain.repository.UserRepository;
import com.hexi.Cerberus.domain.services.UserGroupService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserGroupServiceImpl implements UserGroupService {

    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private GroupUserRelationsRepository groupUserRelationsRepository;

    public UserGroupServiceImpl(UserRepository userRepository, GroupRepository groupRepository, GroupUserRelationsRepository groupUserRelationsRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.groupUserRelationsRepository = groupUserRelationsRepository;
    }

    @Override
    public void deleteGroup(GroupID id) {
        groupRepository.deleteById(id.getId());
    }

    @Override
    public void updateGroup(Group groupEntity) {
        groupRepository.update(groupEntity);
    }

    @Override
    public void createGroup(Group groupEntity) {
        groupEntity.setId(new GroupID(UUID.randomUUID().toString()));
        groupRepository.append(groupEntity);
    }

    @Override
    public Group getGroupById(GroupID id) {
        return groupRepository.getById(id.getId());
    }

    @Override
    public List<Group> getGroups() {
        return groupRepository.getAll();
    }

    @Override
    public void deleteUser(UserID id) {
        userRepository.deleteById(id.getId());
        groupUserRelationsRepository.getAll().removeIf(relation -> relation.getUserId().equals(id));
    }

    @Override
    public void updateUser(User userEntity, List<GroupID> groups) {
        userRepository.update(userEntity);
        updateGroupUserRelations(userEntity, groups);
    }

    @Override
    public void createUser(User userEntity, List<GroupID> groups) {
        userEntity.setId(new UserID(UUID.randomUUID().toString()));
        userRepository.append(userEntity);
        updateGroupUserRelations(userEntity, groups);
    }

    @Override
    public UserGroups getUserById(UserID id) {
        User user = userRepository.getById(id.getId());
        List<GroupUserRelation> relations = groupUserRelationsRepository.getAll()
                .stream()
                .filter(relation -> relation.getUserId().equals(id))
                .collect(Collectors.toList());

        return UserGroups.builder()
                .user(user)
                .groups(getGroupsForUser(relations))
                .build();
    }

    @Override
    public List<UserGroups> getUsers() {
        List<User> users = userRepository.getAll();

        List<GroupUserRelation> relations = groupUserRelationsRepository.getAll();

        return users.stream()
                .map(user -> UserGroups.builder()
                        .user(user)
                        .groups(getGroupsForUser(relations, user.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<Group> getGroupsForUser(List<GroupUserRelation> relations, UserID userId) {
        return relations.stream()
                .filter(relation -> relation.getUserId().equals(userId))
                .map(relation -> groupRepository.getById(relation.getGroupId().getId()))
                .collect(Collectors.toList());
    }

    private List<Group> getGroupsForUser(List<GroupUserRelation> relations) {
        return relations.stream()
                .map(relation -> groupRepository.getById(relation.getGroupId().getId()))
                .collect(Collectors.toList());
    }

    private void updateGroupUserRelations(User userEntity, List<GroupID> groups) {
        groupUserRelationsRepository.getAll().stream().filter(relation -> relation.getUserId().equals(userEntity.getId())).forEach(relation -> groupUserRelationsRepository.deleteById(relation.getId().getId()));
        groupUserRelationsRepository.append(createGroupUserRelations(userEntity, groups));
    }

    private List<GroupUserRelation> createGroupUserRelations(User userEntity, List<GroupID> groups) {
        List<GroupUserRelation> relations = groups.stream()
                .map(groupId -> GroupUserRelation.builder()
                        .id(new UserGroupID(UUID.randomUUID().toString()))
                        .userId(userEntity.getId())
                        .groupId(groupId)
                        .build())
                .collect(Collectors.toList());
        return relations;
    }
}
