package com.hexi.Cerberus.application.warehouse.service;

import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseDetailsDTO;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.application.warehouse.service.command.CreateWareHouseCmd;
import com.hexi.Cerberus.application.warehouse.service.command.UpdateWareHouseDetailsCmd;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public interface WareHouseManagementService {
    Optional<WareHouseDetailsDTO> displayBy(WareHouseID wareHouseId);

    List<WareHouseDetailsDTO> displayAllBy(Query query);

    List<WareHouseDetailsDTO> displayAll();

    WareHouseDetailsDTO create(CreateWareHouseCmd cmd);

    void updateDetails(UpdateWareHouseDetailsCmd cmd);

    void delete(WareHouseID id);
}
