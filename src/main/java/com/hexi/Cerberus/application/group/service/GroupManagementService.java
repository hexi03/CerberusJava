package com.hexi.Cerberus.application.group.service;

import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.group.command.CreateGroupCmd;
import com.hexi.Cerberus.domain.group.command.GroupExcludeUsersCmd;
import com.hexi.Cerberus.domain.group.command.GroupIncludeUsersCmd;
import com.hexi.Cerberus.domain.group.command.UpdateGroupDetailsCmd;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public interface GroupManagementService {

    Optional<Group> displayBy(GroupID id);

    List<Group> displayAll(Query query);

    List<Group> displayAll();

    Group create(CreateGroupCmd cmd);

    void updateDetails(UpdateGroupDetailsCmd cmd);

    void delete(GroupID id);

    void excludeUsers(GroupExcludeUsersCmd build);

    void includeUsers(GroupIncludeUsersCmd build);
}


