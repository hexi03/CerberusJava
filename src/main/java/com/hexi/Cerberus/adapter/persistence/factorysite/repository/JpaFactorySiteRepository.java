package com.hexi.Cerberus.adapter.persistence.factorysite.repository;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.infrastructure.query.Query;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaFactorySiteRepository<T extends FactorySiteModel,ID extends FactorySiteID> extends FactorySiteRepository<T,ID>, JpaRepository<T,ID> {
    @SneakyThrows
    default List<T> findAllWithQuery(Query domainQuery){
        throw new ExecutionControl.NotImplementedException("");
    }
}
