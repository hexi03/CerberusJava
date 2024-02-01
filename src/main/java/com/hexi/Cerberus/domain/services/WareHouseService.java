package com.hexi.Cerberus.domain.services;

import com.hexi.Cerberus.domain.commontypes.DepartmentID;
import com.hexi.Cerberus.domain.commontypes.WareHouseID;
import com.hexi.Cerberus.domain.entities.WareHouse;

import java.util.List;

public interface WareHouseService {
    List<WareHouse> getWareHouses();
    WareHouse getWareHouseById(WareHouseID id);

    List<WareHouse> getWareHouseByDepartmentId(DepartmentID id);

    void createWareHouse(WareHouse WareHouse);
    void updateWareHouse(WareHouse department);
    void deleteWareHouse(WareHouseID id);
}
