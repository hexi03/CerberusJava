package com.hexi.Cerberus.domain.service;

import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.user.User;
import jakarta.transaction.Transactional;

@Transactional
public interface GroupManagementService {
    void addUserToGroup(Group group, User user);

    void removeUserFromGroup(Group group, User user);

}
