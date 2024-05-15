package com.hexi.Cerberus.application.factorysite.service.impl;

import com.hexi.Cerberus.application.factorysite.service.DTO.FactorySiteDetailsDTO;
import com.hexi.Cerberus.application.factorysite.service.FactorySiteDomainToDtoMapper;
import com.hexi.Cerberus.application.factorysite.service.FactorySiteManagementService;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteFactory;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.factorysite.FactorySiteUpdater;
import com.hexi.Cerberus.domain.factorysite.command.CreateFactorySiteCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteDetailsCmd;
import com.hexi.Cerberus.domain.factorysite.command.UpdateFactorySiteSupplyCmd;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AplFactorySiteManagementServiceImpl implements FactorySiteManagementService {
    public final FactorySiteRepository factorySiteRepository;
    public final MessagePublisher messagePublisher;
    public final MutableAclService aclService;
    public final FactorySiteFactory factorySiteFactory;
    public final FactorySiteUpdater factorySiteUpdater;
    public final FactorySiteDomainToDtoMapper factorySiteDomainToDtoMapper;
    public final WareHouseRepository wareHouseRepository;

    @Override
    public Optional<FactorySiteDetailsDTO> displayBy(FactorySiteID id) {
        Optional<FactorySite> factorySite = factorySiteRepository.findById(id);
        return factorySite.map(factorySiteDomainToDtoMapper::factorySiteToDetailsDTO);
    }

    @Override
    public List<FactorySiteDetailsDTO> displayAllBy(Query query) {
        return ((List<FactorySite>)factorySiteRepository.findAllWithQuery(query)).stream()
                .map(factorySiteDomainToDtoMapper::factorySiteToDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FactorySiteDetailsDTO> displayAll() {
        return ((List<FactorySite>)factorySiteRepository.findAll()).stream()
                .map(factorySiteDomainToDtoMapper::factorySiteToDetailsDTO)
                .collect(Collectors.toList());

    }


    @Override
    public FactorySiteDetailsDTO create(CreateFactorySiteCmd cmd) {
        cmd.validate().onFailedThrow();
        FactorySite factorySite = factorySiteFactory.from(cmd);
        factorySiteRepository.append(factorySite);
        factorySite.initAcl(aclService);
        messagePublisher.publish(factorySite.edjectEvents());
        return factorySiteDomainToDtoMapper.factorySiteToDetailsDTO(factorySite);
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
    public void updateSupply(UpdateFactorySiteSupplyCmd cmd) throws RuntimeException {
        cmd.validate().onFailedThrow();
        FactorySite factorySite = (FactorySite) factorySiteRepository.findById(cmd.getFactorySiteId()).orElseThrow();
        Collection<WareHouse> suppliers = cmd.getSuppliers().stream().map(wareHouseID -> factorySite.getParentDepartment().getWareHouses().stream().filter(wareHouse -> wareHouse.getId().equals(wareHouseID)).findAny().orElse(null)).filter(o -> o != null).collect(Collectors.toList());
        log.info("Founded factorysite: " + factorySite);
        log.info("Founded suppliers: " + suppliers);

        factorySite.setSuppliers(suppliers);
        messagePublisher.publish(factorySite.edjectEvents());

        factorySiteRepository.update(factorySite);
    }

    @Override
    public void delete(FactorySiteID id) {
        factorySiteRepository.deleteById(id);
    }
}
