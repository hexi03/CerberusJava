package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.warehouse.ShipmentReport;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;
import java.util.stream.Collectors;
@Entity
@Access(AccessType.FIELD)
public class ShipmentReportModel extends WareHouseReportModel implements ShipmentReport {
    @ManyToMany(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "shipment_report_items_item_entry_assoc")
    Collection<ItemEntry> items = new ArrayList<>();

    public ShipmentReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            Map<ItemModel, Integer> items,
            UserModel creator) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt, creator);
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    public ShipmentReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Map<ItemModel, Integer> items,
            UserModel creator) {
        super(id, wareHouse, createdAt, expirationDate, creator);
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }


    private ShipmentReportModel() {
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
