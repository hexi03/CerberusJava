package com.hexi.Cerberus.application.factorysite.service;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.command.CreateFactorySiteCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteDetailsCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteSupplyCmd;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public interface FactorySiteManagementService {
    Optional<FactorySite> displayBy(FactorySiteID factorySiteId);
    List<FactorySite> displayAllBy(Query query);
    List<FactorySite> displayAllBy();
    FactorySite create(CreateFactorySiteCmd cmd);
    void updateDetails(UpdateFactorySiteDetailsCmd cmd);
    void updateSupply(UpdateFactorySiteSupplyCmd cmd);
    void delete(FactorySiteID id);
}
