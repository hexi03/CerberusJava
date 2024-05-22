package com.hexi.Cerberus.application.group.service;

import com.hexi.Cerberus.application.group.service.DTO.GroupDetailsDTO;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.group.command.*;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public interface GroupManagementService {

    Optional<GroupDetailsDTO> displayBy(GroupID id);

    List<GroupDetailsDTO> displayAllBy(Query query);

    List<GroupDetailsDTO> displayAll();

    GroupDetailsDTO create(CreateGroupCmd cmd);

    void updateDetails(UpdateGroupDetailsCmd cmd);

    void delete(GroupID id);

    void excludeUsers(GroupExcludeUsersCmd build);

    void includeUsers(GroupIncludeUsersCmd build);

    void setUsers(GroupSetUsersCmd build);
}


