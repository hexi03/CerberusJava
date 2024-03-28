package com.hexi.Cerberus.domain.item;

import com.hexi.Cerberus.domain.item.command.CreateItemCmd;

public class ItemFactory {
    public static Item from(CreateItemCmd cmd) {
        return new Item(new ItemID(), cmd.getName(), new Unit(cmd.getUnits()));
    }
}
