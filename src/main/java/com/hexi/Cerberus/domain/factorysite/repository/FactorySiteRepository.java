package com.hexi.Cerberus.domain.factorysite.repository;

import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.infrastructure.repository.Repository;

public interface FactorySiteRepository<T extends FactorySite, ID extends FactorySiteID> extends Repository<T, ID> {

}
