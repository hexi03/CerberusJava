package com.hexi.Cerberus.adapter.persistence.item.impl;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public Optional<Item> displayById(ItemID id) {
        return Optional.empty();
    }

    @Override
    public List<Item> displayById(List<ItemID> itemIDS) {
        return null;
    }

    @Override
    public List<Item> displayAll(Query query) {
        return null;
    }

    @Override
    public List<Item> displayAll() {
        return null;
    }

    @Override
    public Item append(Item user) {
        return null;
    }

    @Override
    public void update(Item user) {

    }

    @Override
    public void deleteById(ItemID id) {

    }

    @Override
    public boolean isExists(ItemID id) {
        return false;
    }
}
