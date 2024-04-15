package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.domain.item.Item;

import java.util.Map;

public interface ItemStorageOperationReport {
    Map<Item, Integer> getItems();
}
