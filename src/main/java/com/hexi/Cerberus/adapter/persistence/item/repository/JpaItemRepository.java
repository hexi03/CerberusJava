package com.hexi.Cerberus.adapter.persistence.item.repository;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItemRepository<T extends ItemModel,ID extends ItemID> extends ItemRepository<T,ID>, JpaRepository<T,ID> {
}
