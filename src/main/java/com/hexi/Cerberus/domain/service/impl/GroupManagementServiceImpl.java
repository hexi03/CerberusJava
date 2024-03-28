package com.hexi.Cerberus.domain.service.impl;

import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.repository.GroupRepository;
import com.hexi.Cerberus.domain.service.GroupManagementService;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GroupManagementServiceImpl implements GroupManagementService {
    public final MessagePublisher messagePublisher;
    public final GroupRepository groupRepository;
    public final UserRepository userRepository;
    @Override
    public void addUserToGroup(Group group, User user) {
        group.addUser(user);

        messagePublisher.publish(group.edjectEvents());
        groupRepository.updateGroup(group);
        userRepository.updateUser(user);
        //TODO ????
    }

    @Override
    public void removeUserFromGroup(Group group, User user) {
        group.removeUser(user);
        messagePublisher.publish(group.edjectEvents());
        groupRepository.updateGroup(group);
        userRepository.updateUser(user);
        //TODO ????
    }
}
