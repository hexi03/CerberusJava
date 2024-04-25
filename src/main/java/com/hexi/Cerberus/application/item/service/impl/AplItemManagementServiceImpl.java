package com.hexi.Cerberus.application.item.service.impl;

import com.hexi.Cerberus.application.item.service.ItemManagementService;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemFactory;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.ItemUpdater;
import com.hexi.Cerberus.domain.item.command.CreateItemCmd;
import com.hexi.Cerberus.domain.item.command.UpdateItemCmd;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AplItemManagementServiceImpl implements ItemManagementService {
    public final ItemRepository itemRepository;
    public final MessagePublisher messagePublisher;
    public final ItemFactory itemFactory;
    public final ItemUpdater itemUpdater;

    @Override
    public Optional<Item> displayBy(ItemID itemID) {
        return itemRepository.findById(itemID);
    }

    @Override
    public List<Item> displayAllBy(Query query) {
        return itemRepository.findAllWithQuery(query);
    }

    @Override
    public List<Item> displayAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item create(CreateItemCmd cmd) {
        cmd.validate().onFailedThrow();
        Item item = itemFactory.from(cmd);
        itemRepository.append(item);
        messagePublisher.publish(item.edjectEvents());
        return item;

    }

    @Override
    public void updateDetails(UpdateItemCmd cmd) {
        cmd.validate().onFailedThrow();
        Optional<Item> item = itemRepository.findById(cmd.getItemId());
        item.orElseThrow(() -> new RuntimeException(String.format("There are no item with id %s", cmd.getItemId().toString())));
        itemUpdater.updateBy(item.get(), cmd);
        itemRepository.update(item.get());
        messagePublisher.publish(item.get().edjectEvents());
    }

    @Override
    public void setDeleted(ItemID id) {
        Optional<Item> item = itemRepository.findById(id);
        item.orElseThrow(() -> new RuntimeException(String.format("There are no warehouse with id %s", id.toString())));
        messagePublisher.publish(item.get().edjectEvents());
        itemRepository.update(item.get());

    }
}
