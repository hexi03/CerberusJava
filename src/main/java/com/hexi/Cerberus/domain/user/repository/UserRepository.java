package com.hexi.Cerberus.domain.user.repository;

import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.infrastructure.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
public interface UserRepository<T extends User, ID extends UserID> extends Repository<T, ID> {

    Optional<User> findByEmail(String email);
}
