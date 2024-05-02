package com.hexi.Cerberus.application.item.service;

import com.hexi.Cerberus.application.item.service.DTO.ItemDetailsDTO;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.command.CreateItemCmd;
import com.hexi.Cerberus.domain.item.command.UpdateItemCmd;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public interface ItemManagementService {
    Optional<ItemDetailsDTO> displayBy(ItemID id);

    List<ItemDetailsDTO> displayAllBy(Query query);

    List<ItemDetailsDTO> displayAll();

    ItemDetailsDTO create(CreateItemCmd cmd);

    void updateDetails(UpdateItemCmd cmd);

    void setDeleted(ItemID id);
}
