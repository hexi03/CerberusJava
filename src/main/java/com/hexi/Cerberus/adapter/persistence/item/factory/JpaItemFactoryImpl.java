package com.hexi.Cerberus.adapter.persistence.item.factory;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemFactory;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.Unit;
import com.hexi.Cerberus.domain.item.command.CreateItemCmd;

public class JpaItemFactoryImpl implements ItemFactory {
    public Item from(CreateItemCmd cmd) {
        return new ItemModel(new ItemID(), cmd.getName(), new Unit(cmd.getUnits()));
    }
}
