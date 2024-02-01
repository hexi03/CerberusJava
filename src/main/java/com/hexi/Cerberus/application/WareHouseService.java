package com.hexi.Cerberus.application;

import com.hexi.Cerberus.domain.commontypes.WareHouseID;
import com.hexi.Cerberus.application.DTO.WareHouseDTO;

import java.util.List;

public interface WareHouseService {
    WareHouseDTO getWareHouseById(WareHouseID id);
    List<WareHouseDTO> getAllWareHouses();
    void createWareHouse(WareHouseDTO dto);
    void updateWareHouse(WareHouseDTO dto);
    void deleteWareHouse(WareHouseID id);
}
