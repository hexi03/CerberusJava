package com.hexi.Cerberus.adapter.persistence.warehouse.impl;

import com.hexi.Cerberus.adapter.persistence.warehouse.WareHouseSpringDataRepository;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public class WareHouseRepositoryImpl implements WareHouseRepository {
    WareHouseSpringDataRepository repository;
    WareHouseModelToDomainConverter modelToDomain;
    @Override
    public Optional<WareHouse> displayById(WareHouseID id) {
        return repository.findById(id.getId()).map(modelToDomain);
    }

    @Override
    public List<WareHouse> displayById(List<WareHouseID> wareHouseIDS) {
        return null;
    }

    @Override
    public List<WareHouse> displayAll(Query query) {
        return null;
    }

    @Override
    public List<WareHouse> displayAll() {
        return null;
    }

    @Override
    public WareHouse append(WareHouse user) {
        return null;
    }

    @Override
    public void update(WareHouse user) {

    }

    @Override
    public void deleteById(WareHouseID id) {

    }

    @Override
    public boolean isExists(WareHouseID id) {
        return false;
    }
}
