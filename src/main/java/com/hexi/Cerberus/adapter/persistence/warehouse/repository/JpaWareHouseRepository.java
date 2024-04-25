package com.hexi.Cerberus.adapter.persistence.warehouse.repository;

import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWareHouseRepository<T extends WareHouseModel, ID extends WareHouseID> extends WareHouseRepository<T, ID>, JpaRepository<T,ID> {
}
