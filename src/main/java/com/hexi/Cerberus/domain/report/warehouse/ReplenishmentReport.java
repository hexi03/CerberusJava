package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;

import java.util.Map;

public interface ReplenishmentReport extends WareHouseReport, ItemStorageOperationReport {
    void setItems(Map<Item, Integer> itemMap);

}
