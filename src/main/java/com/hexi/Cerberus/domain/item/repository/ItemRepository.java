package com.hexi.Cerberus.domain.item.repository;

import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.item.ItemID;
import com.hexi.Cerberus.infrastructure.repository.Repository;

public interface ItemRepository<T extends Item, ID extends ItemID> extends Repository<T, ID> {

}
