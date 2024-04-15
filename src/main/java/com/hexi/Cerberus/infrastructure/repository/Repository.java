package com.hexi.Cerberus.infrastructure.repository;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.infrastructure.entity.Entity;
import com.hexi.Cerberus.infrastructure.entity.EntityId;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity, ID extends EntityId> {
    Optional<T> findById(ID id);

    List<T> findById(List<ID> ids);

    List<T> findAll(Query query);

    List<T> findAll();

    T append(T entity);

    void update(T entity);

    void deleteById(ID id);

    boolean isExists(ID id);

    void save(T entity);
}


