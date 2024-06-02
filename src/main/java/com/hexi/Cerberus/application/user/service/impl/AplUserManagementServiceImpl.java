package com.hexi.Cerberus.application.user.service.impl;

import com.hexi.Cerberus.application.user.service.DTO.UserDetailsDTO;
import com.hexi.Cerberus.application.user.service.UserDomainToDTOMapper;
import com.hexi.Cerberus.application.user.service.UserManagementService;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserFactory;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.UserUpdater;
import com.hexi.Cerberus.application.user.service.command.CreateUserCmd;
import com.hexi.Cerberus.application.user.service.command.UpdateUserDetailsCmd;
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
public class AplUserManagementServiceImpl implements UserManagementService {
    public final UserRepository userRepository;
    public final MessagePublisher messagePublisher;
    public final MutableAclService aclService;
    public final UserFactory userFactory;
    public final UserUpdater userUpdater;
    public final UserDomainToDTOMapper userDomainToDtoMapper;

    @Override
    public Optional<UserDetailsDTO> displayBy(UserID id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userDomainToDtoMapper::userToDetailsDTO);
    }

    @Override
    public Optional<UserDetailsDTO> displayByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userDomainToDtoMapper::userToDetailsDTO);
    }

    @Override
    public List<UserDetailsDTO> displayAllBy(Query query) {
        return ((List<User>)userRepository.findAllWithQuery(query)).stream()
                .map(userDomainToDtoMapper::userToDetailsDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<UserDetailsDTO> displayAll() {
        return ((List<User>)userRepository.findAll()).stream()
                .map(userDomainToDtoMapper::userToDetailsDTO)
                .collect(Collectors.toList());

    }

    @Override
    public UserDetailsDTO create(CreateUserCmd cmd) {
        cmd.validate().onFailedThrow();
        User user = userFactory.from(cmd);
        userRepository.append(user);
        user.initAcl(aclService);
        messagePublisher.publish(user.edjectEvents());
        return userDomainToDtoMapper.userToDetailsDTO(user);
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
