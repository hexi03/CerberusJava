package com.hexi.Cerberus.application.user.service.impl;

import com.hexi.Cerberus.application.user.service.UserManagementService;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserFactory;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.UserUpdater;
import com.hexi.Cerberus.domain.user.command.CreateUserCmd;
import com.hexi.Cerberus.domain.user.command.UpdateUserDetailsCmd;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AplUserManagementServiceImpl implements UserManagementService {
    public final UserRepository userRepository;
    public final MessagePublisher messagePublisher;
    public final MutableAclService aclService;
    public final UserFactory userFactory;
    public final UserUpdater userUpdater;

    @Override
    public Optional<User> displayBy(UserID userID) {
        return userRepository.findById(userID);
    }

    @Override
    public List<User> displayAllBy(Query query) {
        return userRepository.findAllWithQuery(query);
    }

    @Override
    public List<User> displayAllBy() {
        return userRepository.findAll();
    }

    @Override
    public User create(CreateUserCmd cmd) {
        cmd.validate().onFailedThrow();
        User user = userFactory.from(cmd);
        userRepository.append(user);
        user.initAcl(aclService);
        messagePublisher.publish(user.edjectEvents());
        return user;
    }

    @Override
    public void updateDetails(UpdateUserDetailsCmd cmd) {
        cmd.validate().onFailedThrow();
        Optional<User> user = userRepository.findById(cmd.getUserId());
        user.orElseThrow(() -> new RuntimeException(String.format("There are no user with id %s", cmd.getUserId().toString())));
        userUpdater.updateBy(user.get(), cmd);
        userRepository.update(user.get());
        messagePublisher.publish(user.get().edjectEvents());
    }

    @Override
    public void delete(UserID id) {
        userRepository.deleteById(id);
    }
}
