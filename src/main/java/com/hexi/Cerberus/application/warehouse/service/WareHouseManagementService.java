package com.hexi.Cerberus.application.warehouse.service;

import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.command.CreateWareHouseCmd;
import com.hexi.Cerberus.domain.warehouse.command.UpdateWareHouseDetailsCmd;
import com.hexi.Cerberus.infrastructure.query.Query;
import com.hexi.Cerberus.infrastructure.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface WareHouseManagementService {
    Optional<WareHouse> displayBy(WareHouseID wareHouseId);
    List<WareHouse> displayAllBy(Query query);
    List<WareHouse> displayAllBy();
    WareHouse create(CreateWareHouseCmd cmd);
    void updateDetails(UpdateWareHouseDetailsCmd cmd);
    void delete(WareHouseID id);
}
