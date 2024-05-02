package com.hexi.Cerberus.application.user.service;

import com.hexi.Cerberus.application.user.service.DTO.UserDetailsDTO;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.command.CreateUserCmd;
import com.hexi.Cerberus.domain.user.command.UpdateUserDetailsCmd;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public interface UserManagementService {
    Optional<UserDetailsDTO> displayBy(UserID id);

    List<UserDetailsDTO> displayAllBy(Query query);

    List<UserDetailsDTO> displayAll();

    UserDetailsDTO create(CreateUserCmd cmd);

    void updateDetails(UpdateUserDetailsCmd cmd);

    void delete(UserID id);
}
