package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.SupplyRequirementReportModel;
import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import com.hexi.Cerberus.domain.report.warehouse.ReleaseReport;
import com.hexi.Cerberus.domain.user.User;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Access(AccessType.FIELD)
public class ReleaseReportModel extends WareHouseReportModel implements ItemStorageOperationReport, ReleaseReport {
    @ManyToOne(cascade = CascadeType.ALL)
    SupplyRequirementReportModel supplyReqReport;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "release_report_items_item_entry_assoc")
    Collection<ItemEntry> items = new ArrayList<>();

    public ReleaseReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            SupplyRequirementReportModel supplyReqReport,
            Map<ItemModel, Integer> items,
            UserModel creator) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt, creator);
        this.supplyReqReport = supplyReqReport;
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());;
    }

    public ReleaseReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            SupplyRequirementReportModel supplyReqReport,
            Map<ItemModel, Integer> items,
            UserModel creator) {
        super(id, wareHouse, createdAt, expirationDate, creator);
        this.supplyReqReport = supplyReqReport;
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }


    private ReleaseReportModel() {
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
    public void setSupplyReqReportId(Report report) {
        this.supplyReqReport = (SupplyRequirementReportModel) report;
    }

    @Override
    public void setItems(Map<Item, Integer> reqMap) {
        this.items = reqMap.entrySet().stream().map(entry -> new ItemEntry((ItemModel)entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }

    @Override
    public SupplyRequirementReport getSupplyReqReport() {
        return (SupplyRequirementReport) supplyReqReport;
    }
}
