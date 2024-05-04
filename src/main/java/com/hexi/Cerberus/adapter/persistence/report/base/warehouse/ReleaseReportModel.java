package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemEntry;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.SupplyRequirementReportModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Access(AccessType.FIELD)
public class ReleaseReportModel extends WareHouseReportModel implements ItemStorageOperationReport {
    @ManyToOne(cascade = CascadeType.ALL)
    SupplyRequirementReportModel supplyReqReport;
    @OneToMany(cascade = CascadeType.ALL)
    Collection<ItemEntry> items = new ArrayList<>();

    public ReleaseReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            SupplyRequirementReportModel supplyReqReport,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.supplyReqReport = supplyReqReport;
        this.items = items.entrySet().stream().map(entry -> new ItemEntry(entry.getKey(),entry.getValue())).collect(Collectors.toList());;
    }

    public ReleaseReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            SupplyRequirementReportModel supplyReqReport,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate);
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

}
