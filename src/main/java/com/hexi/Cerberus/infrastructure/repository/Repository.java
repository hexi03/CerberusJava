package com.hexi.Cerberus.infrastructure.repository;

import com.hexi.Cerberus.infrastructure.entity.Entity;
import com.hexi.Cerberus.infrastructure.entity.EntityID;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity, ID extends EntityID> {
    Optional<T> findById(ID id);

    List<T> findAllById(Iterable<ID> ids);

    List<T> findAllWithQuery(Query query);

    List<T> findAll();

//    T append(T entity);
//
//    void update(T entity);

    void deleteById(ID id);

    void save(T entity);

    default T append(T entity) {
        save(entity);
        return findById((ID) entity.getId()).orElseThrow();
    }

    default void update(T entity) {
        save(entity);
    }
}


