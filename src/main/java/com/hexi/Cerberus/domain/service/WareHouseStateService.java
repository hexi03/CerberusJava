package com.hexi.Cerberus.domain.service;

import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseState;
import org.springframework.stereotype.Service;

@Service
public interface WareHouseStateService {


    WareHouseState getWareHouseState(WareHouse wareHouse);
}
