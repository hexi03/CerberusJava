package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.WorkShiftReportModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;
@Entity
@Access(AccessType.FIELD)
public class WorkShiftReplenishmentReportModel extends WareHouseReportModel implements ItemStorageOperationReport {
    @ManyToOne(cascade = CascadeType.ALL)
    WorkShiftReportModel workShiftReport;
    @OneToMany(cascade = CascadeType.ALL)
    Collection<ItemEntry> items = new ArrayList<>();
    //Невостребованные остатки на возврат
    @OneToMany(cascade = CascadeType.ALL)
    Collection<ItemEntry> unclaimedRemains = new ArrayList<>();

    public WorkShiftReplenishmentReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            WorkShiftReportModel workShiftReport,
            Map<ItemModel, Integer> items,
            Map<ItemModel, Integer> unclaimedRemains) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.unclaimedRemains = unclaimedRemains.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.workShiftReport = workShiftReport;
    }

    public WorkShiftReplenishmentReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            WorkShiftReportModel workShiftReport,
            Map<ItemModel, Integer> items,
            Map<ItemModel, Integer> unclaimedRemains) {
        super(id, wareHouse, createdAt, expirationDate);
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.unclaimedRemains = unclaimedRemains.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());

        this.workShiftReport = workShiftReport;
    }


    private WorkShiftReplenishmentReportModel() {
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

}
