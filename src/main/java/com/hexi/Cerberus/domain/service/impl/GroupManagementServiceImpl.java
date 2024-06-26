package com.hexi.Cerberus.domain.service.impl;

import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.repository.GroupRepository;
import com.hexi.Cerberus.domain.service.GroupManagementService;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Transactional
public class GroupManagementServiceImpl implements GroupManagementService {
    public final MessagePublisher messagePublisher;
    public final GroupRepository groupRepository;
    public final UserRepository userRepository;

    @Override
    public void addUserToGroup(Group group, User user) {
        group.addUser(user);

        messagePublisher.publish(group.edjectEvents());
        groupRepository.update(group);
        userRepository.update(user);
        //TODO ????
    }

    @Override
    public void removeUserFromGroup(Group group, User user) {
        group.removeUser(user.getId());
        messagePublisher.publish(group.edjectEvents());
        groupRepository.update(group);
        userRepository.update(user);
        //TODO ????
    }
}
