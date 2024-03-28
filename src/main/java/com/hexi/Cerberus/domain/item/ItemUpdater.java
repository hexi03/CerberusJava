package com.hexi.Cerberus.domain.item;

import com.hexi.Cerberus.domain.item.command.UpdateItemCmd;

public class ItemUpdater {
    public void updateBy(Item item, UpdateItemCmd cmd) {
        item.setName(cmd.getName());
        item.setUnit(new Unit(cmd.getUnits()));
    }
}
