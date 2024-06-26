package com.hexi.Cerberus.application.group.service.impl;

import com.hexi.Cerberus.application.group.service.DTO.GroupDetailsDTO;
import com.hexi.Cerberus.application.group.service.GroupDomainToDTOMapper;
import com.hexi.Cerberus.application.group.service.GroupManagementService;
import com.hexi.Cerberus.application.group.service.command.*;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupFactory;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.group.GroupUpdater;
import com.hexi.Cerberus.domain.group.event.GroupDeletedEvent;
import com.hexi.Cerberus.domain.group.repository.GroupRepository;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class AplGroupManagementServiceImpl implements GroupManagementService {
    public final GroupRepository groupRepository;
    public final UserRepository userRepository;
    public final MessagePublisher messagePublisher;
    public final MutableAclService aclService;
    public final GroupFactory groupFactory;
    public final GroupUpdater groupUpdater;
    public final GroupDomainToDTOMapper groupDomainToDtoMapper;

    @Override
    public Optional<GroupDetailsDTO> displayBy(GroupID id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.map(groupDomainToDtoMapper::groupToDetailsDTO);
    }

    @Override
    public List<GroupDetailsDTO> displayAllBy(Query query) {
        return ((List<Group>)groupRepository.findAllWithQuery(query)).stream()
                .map(groupDomainToDtoMapper::groupToDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupDetailsDTO> displayAll() {
        return ((List<Group>)groupRepository.findAll()).stream()
                .map(groupDomainToDtoMapper::groupToDetailsDTO)
                .collect(Collectors.toList());

    }

    @Override
    public GroupDetailsDTO create(CreateGroupCmd cmd) {
        cmd.validate().onFailedThrow();
        Group group = groupFactory.from(cmd);
        groupRepository.append(group);
        group.initAcl(aclService);
        messagePublisher.publish(group.edjectEvents());
        return groupDomainToDtoMapper.groupToDetailsDTO(group);

    }

    @Override
    public void updateDetails(UpdateGroupDetailsCmd cmd) {
        cmd.validate().onFailedThrow();
        Optional<Group> group = groupRepository.findById(cmd.getGroupId());
        group.orElseThrow(() -> new RuntimeException(String.format("There are no group with id %s", cmd.getGroupId().toString())));
        groupUpdater.updateBy(group.get(), cmd);
        groupRepository.update(group.get());
        messagePublisher.publish(group.get().edjectEvents());
    }

//    @Override
//    public void setDeleted(GroupID id) {
//        Optional<Group> group = groupRepository.displayById(id);
//        group.orElseThrow(() -> new RuntimeException(String.format("There are no warehouse with id %s", id.toString())));
//        messagePublisher.publish(group.get().edjectEvents());
//        groupRepository.update(group.get());
//
//    }


    @Override
    public void delete(GroupID id) {
        groupRepository.deleteById(id);
        messagePublisher.publish(List.of(new GroupDeletedEvent(id)));

    }

    @Override
    public void includeUsers(GroupIncludeUsersCmd cmd) {
        Optional<Group> group = groupRepository.findById(cmd.getGroupId());
        group.orElseThrow(() -> new RuntimeException(String.format("There are no group with id %s", cmd.getGroupId().toString())));

        List<User> users =
                cmd
                        .getUsers()
                        .stream()
                        .map(userRepository::findById)
                        .filter(Optional::isPresent)
                        .map(user1 -> (User) user1.get())
                        .toList();

        users.stream().forEach(group.get()::addUser);

        messagePublisher.publish(group.get().edjectEvents());
        groupRepository.update(group.get());
        users.stream().forEach(userRepository::update);

    }

    @Override
    public void setUsers(GroupSetUsersCmd cmd) {
        Optional<Group> group = groupRepository.findById(cmd.getGroupId());
        group.orElseThrow(() -> new RuntimeException(String.format("There are no group with id %s", cmd.getGroupId().toString())));

        List<User> users =
                cmd
                        .getUsers()
                        .stream()
                        .map(userRepository::findById)
                        .filter(Optional::isPresent)
                        .map(user1 -> (User) user1.get())
                        .toList();

        group.get().setUsers(users);

        messagePublisher.publish(group.get().edjectEvents());
        groupRepository.update(group.get());
        users.stream().forEach(userRepository::update);
    }

    @Override
    public void excludeUsers(GroupExcludeUsersCmd build) {
        Optional<Group> group = groupRepository.findById(build.getGroupId());
        group.orElseThrow(() -> new RuntimeException(String.format("There are no group with id %s", build.getGroupId().toString())));

        List<User> users = build
                .getUsers()
                .stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(user -> (User) user.get())
                .toList();

        List<User> removed_users = users.stream().map(user -> group.get().removeUser(user.getId())).collect(Collectors.toList());

        messagePublisher.publish(group.get().edjectEvents());
        groupRepository.update(group.get());
        removed_users.stream().forEach(userRepository::update);
    }

}
