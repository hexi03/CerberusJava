package com.hexi.Cerberus.domain.item;

import com.hexi.Cerberus.application.item.service.command.UpdateItemCmd;
import org.springframework.stereotype.Component;

@Component
public class ItemUpdater {
    public void updateBy(Item item, UpdateItemCmd cmd) {
        item.setName(cmd.getName());
        item.setUnit(new Unit(cmd.getUnits()));
    }
}
