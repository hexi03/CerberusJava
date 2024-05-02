package com.hexi.Cerberus.domain.service;

import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseState;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
public interface WareHouseStateService {


    WareHouseState getWareHouseState(WareHouse wareHouse);
}
