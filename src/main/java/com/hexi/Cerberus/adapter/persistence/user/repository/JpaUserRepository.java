package com.hexi.Cerberus.adapter.persistence.user.repository;

import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.infrastructure.query.Query;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface JpaUserRepository<T extends UserModel, ID extends UserID> extends UserRepository<T, ID>, JpaRepository<T,ID> {
    @SneakyThrows
    default List<T> findAllWithQuery(Query domainQuery){
        throw new ExecutionControl.NotImplementedException("");
    }

}
