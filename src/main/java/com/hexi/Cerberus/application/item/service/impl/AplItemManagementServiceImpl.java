package com.hexi.Cerberus.application.item.service.impl;

import com.hexi.Cerberus.application.group.service.DTO.GroupDetailsDTO;
import com.hexi.Cerberus.application.item.service.DTO.ItemDetailsDTO;
import com.hexi.Cerberus.application.item.service.ItemDomainToDTOMapper;
import com.hexi.Cerberus.application.item.service.ItemManagementService;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemFactory;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.ItemUpdater;
import com.hexi.Cerberus.domain.item.command.CreateItemCmd;
import com.hexi.Cerberus.domain.item.command.UpdateItemCmd;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.infrastructure.messaging.MessagePublisher;
import com.hexi.Cerberus.infrastructure.query.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class AplItemManagementServiceImpl implements ItemManagementService {
    public final ItemRepository itemRepository;
    public final MessagePublisher messagePublisher;
    public final ItemFactory itemFactory;
    public final ItemUpdater itemUpdater;
    public final ItemDomainToDTOMapper itemDomainToDtoMapper;

    @Override
    public Optional<ItemDetailsDTO> displayBy(ItemID id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.map(itemDomainToDtoMapper::itemToDetailsDTO);
    }

    @Override
    public List<ItemDetailsDTO> displayAllBy(Query query) {
        return ((List<Item>)itemRepository.findAllWithQuery(query)).stream()
                .map(itemDomainToDtoMapper::itemToDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDetailsDTO> displayAll() {
        return ((List<Item>)itemRepository.findAll()).stream()
                .map(itemDomainToDtoMapper::itemToDetailsDTO)
                .collect(Collectors.toList());

    }

    @Override
    public ItemDetailsDTO create(CreateItemCmd cmd) {
        cmd.validate().onFailedThrow();
        Item item = itemFactory.from(cmd);
        itemRepository.append(item);
        messagePublisher.publish(item.edjectEvents());
        return itemDomainToDtoMapper.itemToDetailsDTO(item);

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
