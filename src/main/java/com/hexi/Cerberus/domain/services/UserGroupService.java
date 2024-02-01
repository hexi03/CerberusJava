package com.hexi.Cerberus.domain.services;

import com.hexi.Cerberus.domain.commontypes.GroupID;
import com.hexi.Cerberus.domain.commontypes.UserID;
import com.hexi.Cerberus.domain.aggregates.UserGroups;
import com.hexi.Cerberus.domain.entities.Group;
import com.hexi.Cerberus.domain.entities.User;

import java.util.List;

public interface UserGroupService {

    void deleteGroup(GroupID id);

    void updateGroup(Group groupEntity);

    void createGroup(Group groupEntity);

    Group getGroupById(GroupID id);

    List<Group> getGroups();

    void deleteUser(UserID id);

    void updateUser(User userEntity, List<GroupID> groups);

    void createUser(User userEntity, List<GroupID> groups);

    UserGroups getUserById(UserID id);

    List<UserGroups> getUsers();
}
