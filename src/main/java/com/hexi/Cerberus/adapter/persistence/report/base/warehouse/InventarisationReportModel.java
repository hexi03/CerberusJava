package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Access(AccessType.FIELD)
public class InventarisationReportModel extends WareHouseReportModel {
    @OneToMany(cascade = CascadeType.ALL)
    Collection<ItemEntry> items = new ArrayList<>();

    public InventarisationReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    public InventarisationReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate);
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    private InventarisationReportModel() {
        super();
    }

}
