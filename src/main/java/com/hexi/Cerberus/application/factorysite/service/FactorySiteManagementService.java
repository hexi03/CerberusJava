package com.hexi.Cerberus.application.factorysite.service;

import com.hexi.Cerberus.application.factorysite.service.DTO.FactorySiteDetailsDTO;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.command.CreateFactorySiteCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteDetailsCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteSupplyCmd;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public interface FactorySiteManagementService {
    Optional<FactorySiteDetailsDTO> displayBy(FactorySiteID factorySiteId);

    List<FactorySiteDetailsDTO> displayAllBy(Query query);

    List<FactorySiteDetailsDTO> displayAll();

    FactorySiteDetailsDTO create(CreateFactorySiteCmd cmd);

    void updateDetails(UpdateFactorySiteDetailsCmd cmd);

    void updateSupply(UpdateFactorySiteSupplyCmd cmd) throws RuntimeException;

    void delete(FactorySiteID id);
}
