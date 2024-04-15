package com.hexi.Cerberus.application.factorysite.service.impl;

import com.hexi.Cerberus.application.factorysite.service.FactorySiteManagementService;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteFactory;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteUpdater;
import com.hexi.Cerberus.domain.factorysite.command.CreateFactorySiteCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteDetailsCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteSupplyCmd;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.MutableAclService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FactorySiteManagementServiceImpl implements FactorySiteManagementService {
    public final FactorySiteRepository factorySiteRepository;
    public final MessagePublisher messagePublisher;
    public final MutableAclService aclService;
    public final FactorySiteFactory factorySiteFactory;
    public final FactorySiteUpdater factorySiteUpdater;

    @Override
    public Optional<FactorySite> displayBy(FactorySiteID factorySiteId) {
        return factorySiteRepository.findById(factorySiteId);
    }

    @Override
    public List<FactorySite> displayAllBy(Query query) {
        return factorySiteRepository.findAll(query);
    }

    @Override
    public List<FactorySite> displayAllBy() {
        return factorySiteRepository.findAll();
    }

    @Override
    public FactorySite create(CreateFactorySiteCmd cmd) {
        cmd.validate().onFailedThrow();
        FactorySite factorySite = factorySiteFactory.from(cmd);
        factorySiteRepository.append(factorySite);
        factorySite.initAcl(aclService);
        messagePublisher.publish(factorySite.edjectEvents());
        return factorySite;
    }

    @Override
    public void updateDetails(UpdateFactorySiteDetailsCmd cmd) {
        cmd.validate().onFailedThrow();
        Optional<FactorySite> factorySite = factorySiteRepository.findById(cmd.getFactorySiteId());
        factorySite.orElseThrow(() -> new RuntimeException(String.format("There are no factorysite with id %s", cmd.getFactorySiteId().toString())));
        factorySiteUpdater.updateBy(factorySite.get(), cmd);
        factorySiteRepository.update(factorySite.get());
        messagePublisher.publish(factorySite.get().edjectEvents());


    }

    @Override
    public void updateSupply(UpdateFactorySiteSupplyCmd cmd) {
        cmd.validate().onFailedThrow();
        Optional<FactorySite> factorySite = factorySiteRepository.findById(cmd.getFactorySiteId());
        factorySite.orElseThrow(() -> new RuntimeException(String.format("There are no factorysite with id %s", cmd.getFactorySiteId().toString())));
        messagePublisher.publish(factorySite.get().edjectEvents());
        factorySiteRepository.update(factorySite.get());
    }

    @Override
    public void delete(FactorySiteID id) {
        factorySiteRepository.deleteById(id);
    }
}
