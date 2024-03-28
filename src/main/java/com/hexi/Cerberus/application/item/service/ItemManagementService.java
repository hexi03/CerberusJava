package com.hexi.Cerberus.application.item.service;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.command.CreateItemCmd;
import com.hexi.Cerberus.domain.item.command.UpdateItemCmd;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public interface ItemManagementService {
    Optional<Item> displayBy(ItemID id);
    List<Item> displayAllBy(Query query);
    List<Item> displayAll();
    Item create(CreateItemCmd cmd);
    void updateDetails(UpdateItemCmd cmd);
    void setDeleted(ItemID id);
}
