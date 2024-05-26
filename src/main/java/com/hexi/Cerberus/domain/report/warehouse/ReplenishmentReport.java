package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.item.Item;

import java.util.Map;

public interface ReplenishmentReport extends WareHouseReport, ItemReplenish {
    void setItems(Map<Item, Integer> itemMap);
    @Override
    default Map<Item, Integer> getSummaryReplenish(){
        return getItems();
    }

}
