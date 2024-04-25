package com.hexi.Cerberus.adapter.persistence.user.repository;

import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository<T extends UserModel, ID extends UserID> extends UserRepository<T, ID>, JpaRepository<T,ID> {
}
