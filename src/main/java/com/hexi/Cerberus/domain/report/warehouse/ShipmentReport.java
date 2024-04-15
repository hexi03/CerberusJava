package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;

import java.util.Map;

public interface ShipmentReport extends WareHouseReport, ItemStorageOperationReport {
    void setItems(Map<Item, Integer> itMap);

}
