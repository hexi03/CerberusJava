package com.hexi.Cerberus.domain.repository;

import com.hexi.Cerberus.domain.entities.FactorySite;
import com.hexi.Cerberus.infrastructure.repository.Repository;

import java.util.List;

public interface FactorySiteRepository extends Repository<FactorySite> {
    List<FactorySite> getByDepartmentId(String id);
}

