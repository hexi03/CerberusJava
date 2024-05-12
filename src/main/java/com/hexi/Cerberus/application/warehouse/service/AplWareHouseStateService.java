package com.hexi.Cerberus.application.warehouse.service;

import com.hexi.Cerberus.application.warehouse.service.DTO.WareHouseStateDTO;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;

public interface AplWareHouseStateService {
    WareHouseStateDTO getWareHouseState(WareHouseID id);

}
