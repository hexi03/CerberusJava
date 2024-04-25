package com.hexi.Cerberus.domain.warehouse.repository;

import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.infrastructure.repository.Repository;

public interface WareHouseRepository<T extends WareHouse, ID extends WareHouseID> extends Repository<T, ID> {


}
