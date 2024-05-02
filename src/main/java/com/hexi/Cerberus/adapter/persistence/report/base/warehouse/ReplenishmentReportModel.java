package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class ReplenishmentReportModel extends WareHouseReportModel implements ItemStorageOperationReport {
    Map<ItemModel, Integer> items = new HashMap<>();

    public ReplenishmentReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.items = items;
    }

    public ReplenishmentReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate);
        this.items = items;
    }

    private ReplenishmentReportModel() {
        super();
    }

    @Override
    public Map<Item, Integer> getItems() {
        return items
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> entry.getValue()
                        )
                );
    }

}
