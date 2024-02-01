package com.hexi.Cerberus.domain.repository;

import com.hexi.Cerberus.domain.entities.WareHouse;
import com.hexi.Cerberus.infrastructure.repository.Repository;

import java.util.List;

public interface WareHouseRepository extends Repository<WareHouse> {

    List<WareHouse> getByDepartmentId(String id);
}