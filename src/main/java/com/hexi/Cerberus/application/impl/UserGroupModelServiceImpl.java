package com.hexi.Cerberus.application.impl;

import com.hexi.Cerberus.domain.commontypes.GroupID;
import com.hexi.Cerberus.domain.commontypes.UserID;
import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels.*;
import com.hexi.Cerberus.application.Mapper;
import com.hexi.Cerberus.application.UserGroupModelService;
import com.hexi.Cerberus.domain.aggregates.UserGroups;
import com.hexi.Cerberus.domain.entities.Group;
import com.hexi.Cerberus.domain.entities.User;
import com.hexi.Cerberus.domain.services.UserGroupService;

import java.util.List;
import java.util.stream.Collectors;

public class UserGroupModelServiceImpl implements UserGroupModelService {

    private UserGroupService domainUserGroupService;

    // Добавьте мапперы
    private Mapper mapper;

    public UserGroupModelServiceImpl(
            UserGroupService userGroupService,
            Mapper mapper
    ) {
        domainUserGroupService = userGroupService;
        this.mapper = mapper;
    }

    @Override
    public List<UserDetailsModel> getUsers() {
        List<UserGroups> users = domainUserGroupService.getUsers();
        return users.stream()
                .map(mapper::mapUserDetailsModel)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsModel getUserDetailsModel(UserID id) {
        UserGroups user = domainUserGroupService.getUserById(id);
        return mapper.mapUserDetailsModel(user);
    }

    @Override
    public UserCreateModel getUserCreateModel() {

        return UserCreateModel.builder().build();
    }


    @Override
    public void createUser(UserCreateModel user) {
        User userEntity = mapper.mapUserGroups(user);
        domainUserGroupService.createUser(userEntity, user.groups);
    }

    @Override
    public void updateUser(UserEditModel user) {
        User userEntity = mapper.mapUserGroups(user);
        domainUserGroupService.updateUser(userEntity, user.groups);
    }

    @Override
    public void deleteUser(UserID id) {
        domainUserGroupService.deleteUser(id);
    }

    @Override
    public List<GroupDetailsModel> getGroups() {
        List<Group> groups = domainUserGroupService.getGroups();
        return groups.stream()
                .map(mapper::mapGroupDetailsModel)
                .collect(Collectors.toList());
    }

    @Override
    public GroupDetailsModel getGroupDetailsModel(GroupID id) {
        Group group = domainUserGroupService.getGroupById(id);
        return mapper.mapGroupDetailsModel(group);
    }

    @Override
    public void createGroup(GroupCreateModel group) {
        Group groupEntity = mapper.mapGroup(group);
        domainUserGroupService.createGroup(groupEntity);
    }

    @Override
    public void updateGroup(GroupEditModel group) {
        Group groupEntity = mapper.mapGroup(group);
        domainUserGroupService.updateGroup(groupEntity);
    }

    @Override
    public void deleteGroup(GroupID id) {
        domainUserGroupService.deleteGroup(id);
    }

    @Override
    public IndexModel getIndexModel() {
        List<GroupDetailsModel> groups = getGroups();
        List<UserDetailsModel> users = getUsers();

        return new IndexModel(groups, users);
    }

    @Override
    public GroupEditModel getGroupEditModel(GroupID id) {
        Group group = domainUserGroupService.getGroupById(id);
        return mapper.mapGroupEditModel(group);
    }

    @Override
    public UserEditModel getUserEditModel(UserID id) {
        UserGroups user = domainUserGroupService.getUserById(id);
        return mapper.mapUserEditModel(user);
    }

    @Override
    public UserCreateModel regenerate(UserCreateModel model){
        model.setGroupModels(
                model
                        .getGroups()
                        .stream()
                        .map( gid ->
                                mapper.mapUserCreateGroupModel(domainUserGroupService.getGroupById(gid))
                        )
                        .collect(Collectors.toList())
        );
        return model;
    }
    @Override
    public UserEditModel regenerate(UserEditModel model){
        model.setGroupModels(
                model
                        .getGroups()
                        .stream()
                        .map( gid ->
                                mapper.mapUserEditGroupModel(domainUserGroupService.getGroupById(gid))
                        )
                        .collect(Collectors.toList())
        );
        return model;
    }
}
