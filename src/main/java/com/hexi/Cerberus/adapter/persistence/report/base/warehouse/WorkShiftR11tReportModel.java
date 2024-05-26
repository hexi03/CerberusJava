package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.WorkShiftReportModel;
import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.WorkShiftReport;
import com.hexi.Cerberus.domain.report.warehouse.WorkShiftReplenishmentReport;
import com.hexi.Cerberus.domain.user.User;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;
@Entity
@Access(AccessType.FIELD)
public class WorkShiftR11tReportModel extends WareHouseReportModel implements WorkShiftReplenishmentReport {
    @ManyToOne(cascade = CascadeType.ALL)
    WorkShiftReportModel workShiftReport;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "work_shift_repl_report_items_product_entry_assoc")
    Collection<ItemEntry> items = new ArrayList<>();
    //Невостребованные остатки на возврат
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "work_shift_repl_report_unclaimed_remains_item_entry_assoc")
    Collection<ItemEntry> unclaimedRemains = new ArrayList<>();

    public WorkShiftR11tReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            WorkShiftReportModel workShiftReport,
            Map<ItemModel, Integer> items,
            Map<ItemModel, Integer> unclaimedRemains,
            UserModel creator) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt, creator);
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.unclaimedRemains = unclaimedRemains.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.workShiftReport = workShiftReport;
    }

    public WorkShiftR11tReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            WorkShiftReportModel workShiftReport,
            Map<ItemModel, Integer> items,
            Map<ItemModel, Integer> unclaimedRemains,
            UserModel creator) {
        super(id, wareHouse, createdAt, expirationDate, creator);
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
        this.unclaimedRemains = unclaimedRemains.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());

        this.workShiftReport = workShiftReport;
    }


    private WorkShiftR11tReportModel() {
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
    public void setWorkShiftReport(WorkShiftReport report) {
        this.workShiftReport = (WorkShiftReportModel) report;
    }

    @Override
    public void setItems(Map<Item, Integer> reqMap) {
        this.items = reqMap.entrySet().stream().map(entry -> new ItemEntry((ItemModel)entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    @Override
    public Map<Item, Integer> getUnclaimedRemains() {
        return unclaimedRemains
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getItem(),
                                entry -> entry.getAmount()
                        )
                );
    }

    @Override
    public WorkShiftReport getWorkShiftReport() {
        return (WorkShiftReport) workShiftReport;
    }
}
