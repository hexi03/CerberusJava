package com.hexi.Cerberus.domain.user.repository;

import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.infrastructure.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, UserID> {

    Optional<User> findByEmail(String email);
}
