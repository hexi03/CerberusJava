package com.hexi.Cerberus.application.user.service;

import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.command.CreateUserCmd;
import com.hexi.Cerberus.domain.user.command.UpdateUserDetailsCmd;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public interface UserManagementService {
    Optional<User> displayBy(UserID id);
    List<User> displayAllBy(Query query);
    List<User> displayAllBy();
    User create(CreateUserCmd cmd);
    void updateDetails(UpdateUserDetailsCmd cmd);
    void delete(UserID id);
}
