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

public interface Repository<T,ID extends EntityId> {
    Optional<T> displayById(ID id);
    List<T> displayById(List<ID> ids);

    List<T> displayAll(Query query);

    List<T> displayAll();

    T append(T user);

    void update(T user);

    void deleteById(ID id);

    boolean isExists(ID id);
}


