package com.hexi.Cerberus.infrastructure.repository;

import java.util.List;

public interface Repository<T> {
    T getById(String id);

    List<T> getAll();

    T append(T user);

    void update(T user);

    void deleteById(String id);

    boolean isExists(String id);
}


