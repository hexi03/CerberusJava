package com.hexi.Cerberus.adapter.persistence.factorysite.impl;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public class FactorySiteRepositoryImpl implements FactorySiteRepository {
    @Override
    public Optional<FactorySite> displayById(FactorySiteID id) {
        return Optional.empty();
    }

    @Override
    public List<FactorySite> displayById(List<FactorySiteID> factorySiteIDS) {
        return null;
    }

    @Override
    public List<FactorySite> displayAll(Query query) {
        return null;
    }

    @Override
    public List<FactorySite> displayAll() {
        return null;
    }

    @Override
    public FactorySite append(FactorySite user) {
        return null;
    }

    @Override
    public void update(FactorySite user) {

    }

    @Override
    public void deleteById(FactorySiteID id) {

    }

    @Override
    public boolean isExists(FactorySiteID id) {
        return false;
    }
}
