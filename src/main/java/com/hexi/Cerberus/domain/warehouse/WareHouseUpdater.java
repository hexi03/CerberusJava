package com.hexi.Cerberus.domain.warehouse;

import com.hexi.Cerberus.domain.warehouse.command.UpdateWareHouseDetailsCmd;
import org.springframework.stereotype.Component;

@Component
public class WareHouseUpdater {
    public void updateBy(WareHouse wareHouse, UpdateWareHouseDetailsCmd cmd) {
        wareHouse.setName(cmd.getName());
    }
}