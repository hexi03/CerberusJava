package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.warehouse.ReplenishmentReport;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Access(AccessType.FIELD)
public class ReplenishmentReportModel extends WareHouseReportModel implements ItemStorageOperationReport, ReplenishmentReport {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "replenishment_report_items_item_entry_assoc")
    Collection<ItemEntry> items = new ArrayList<>();

    public ReplenishmentReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    public ReplenishmentReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate);
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    private ReplenishmentReportModel() {
        super();
    }

    @Override
    public Map<Item, Integer> getItems() {
        return items
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getItem(),
                                entry -> entry.getAmount()
                        )
                );
    }

    @Override
    public void setItems(Map<Item, Integer> reqMap) {
        this.items = reqMap.entrySet().stream().map(entry -> new ItemEntry((ItemModel)entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }
}
