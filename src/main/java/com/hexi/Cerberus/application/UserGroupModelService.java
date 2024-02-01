package com.hexi.Cerberus.application;

import com.hexi.Cerberus.domain.commontypes.GroupID;
import com.hexi.Cerberus.domain.commontypes.UserID;
import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels.*;

import java.util.List;

public interface UserGroupModelService {

    List<UserDetailsModel> getUsers();

    UserDetailsModel getUserDetailsModel(UserID id);
    UserCreateModel getUserCreateModel();

    void createUser(UserCreateModel user);

    void updateUser(UserEditModel user);

    void deleteUser(UserID id);

    List<GroupDetailsModel> getGroups();

    GroupDetailsModel getGroupDetailsModel(GroupID id);

    void createGroup(GroupCreateModel group);

    void updateGroup(GroupEditModel group);

    void deleteGroup(GroupID id);

    IndexModel getIndexModel();

    GroupEditModel getGroupEditModel(GroupID id);

    UserEditModel getUserEditModel(UserID id);

    UserCreateModel regenerate(UserCreateModel model);

    UserEditModel regenerate(UserEditModel model);
}


