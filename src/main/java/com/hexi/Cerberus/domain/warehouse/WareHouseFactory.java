package com.hexi.Cerberus.domain.warehouse;

import com.hexi.Cerberus.application.warehouse.service.command.CreateWareHouseCmd;
import org.springframework.stereotype.Component;

@Component
public interface WareHouseFactory {
    WareHouse from(CreateWareHouseCmd cmd);
}
