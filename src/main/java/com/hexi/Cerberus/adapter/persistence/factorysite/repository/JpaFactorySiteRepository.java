package com.hexi.Cerberus.adapter.persistence.factorysite.repository;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFactorySiteRepository<T extends FactorySiteModel,ID extends FactorySiteID> extends FactorySiteRepository<T,ID>, JpaRepository<T,ID> {
}
