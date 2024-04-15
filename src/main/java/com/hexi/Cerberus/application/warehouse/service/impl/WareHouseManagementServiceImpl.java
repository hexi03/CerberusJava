package com.hexi.Cerberus.application.warehouse.service.impl;

import com.hexi.Cerberus.application.warehouse.service.WareHouseManagementService;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseFactory;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.WareHouseUpdater;
import com.hexi.Cerberus.domain.warehouse.command.CreateWareHouseCmd;
import com.hexi.Cerberus.domain.warehouse.command.UpdateWareHouseDetailsCmd;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.MutableAclService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WareHouseManagementServiceImpl implements WareHouseManagementService {
    public final WareHouseRepository wareHouseRepository;
    public final MessagePublisher messagePublisher;
    public final MutableAclService aclService;
    public final WareHouseFactory wareHouseFactory;
    public final WareHouseUpdater wareHouseUpdater;

    @Override
    public Optional<WareHouse> displayBy(WareHouseID wareHouseID) {
        return wareHouseRepository.findById(wareHouseID);
    }

    @Override
    public List<WareHouse> displayAllBy(Query query) {
        return wareHouseRepository.findAll(query);
    }

    @Override
    public List<WareHouse> displayAllBy() {
        return wareHouseRepository.findAll();
    }

    @Override
    public WareHouse create(CreateWareHouseCmd cmd) {
        cmd.validate().onFailedThrow();
        WareHouse wareHouse = wareHouseFactory.from(cmd);
        wareHouseRepository.append(wareHouse);
        wareHouse.initAcl(aclService);
        messagePublisher.publish(wareHouse.edjectEvents());
        return wareHouse;

    }

    @Override
    public void updateDetails(UpdateWareHouseDetailsCmd cmd) {
        cmd.validate().onFailedThrow();
        Optional<WareHouse> wareHouse = wareHouseRepository.findById(cmd.getWareHouseId());
        wareHouse.orElseThrow(() -> new RuntimeException(String.format("There are no warehouse with id %s", cmd.getWareHouseId().toString())));
        wareHouseUpdater.updateBy(wareHouse.get(), cmd);
        wareHouseRepository.update(wareHouse.get());
        messagePublisher.publish(wareHouse.get().edjectEvents());


    }

    @Override
    public void delete(WareHouseID id) {
        wareHouseRepository.deleteById(id);
    }
}
