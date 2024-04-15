package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShipmentReportModel extends WareHouseReportModel implements ItemStorageOperationReport {
    Map<ItemModel, Integer> items = new HashMap<>();

    public ShipmentReportModel(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.items = items;
    }

    public ShipmentReportModel(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate);
        this.items = items;
    }


    private ShipmentReportModel() {
        super();
    }

    @Override
    public Map<Item, Integer> getItems() {
        return items
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> (Item) entry.getKey(),
                                entry -> entry.getValue()
                        )
                );
    }
}
