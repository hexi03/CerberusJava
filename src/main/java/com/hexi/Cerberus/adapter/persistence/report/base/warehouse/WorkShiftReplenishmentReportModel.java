package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.factorysite.base.FactorySiteModel;
import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkShiftReplenishmentReportModel extends WareHouseReportModel implements ItemStorageOperationReport {
    FactorySiteModel factorySiteId;
    ReportModel workShiftReportId;
    Map<ItemModel, Integer> items = new HashMap<>();
    //Невостребованные остатки на возврат
    Map<ItemModel, Integer> unclaimedRemains;

    public WorkShiftReplenishmentReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            FactorySiteModel factorySiteId,
            ReportModel workShiftReportId,
            Map<ItemModel, Integer> items,
            Map<ItemModel, Integer> unclaimedRemains) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.items = items;
        this.unclaimedRemains = unclaimedRemains;
        this.factorySiteId = factorySiteId;
        this.workShiftReportId = workShiftReportId;
    }

    public WorkShiftReplenishmentReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            FactorySiteModel factorySiteId,
            ReportModel workShiftReportId,
            Map<ItemModel, Integer> items,
            Map<ItemModel, Integer> unclaimedRemains) {
        super(id, wareHouse, createdAt, expirationDate);
        this.items = items;
        this.unclaimedRemains = unclaimedRemains;
        this.factorySiteId = factorySiteId;
        this.workShiftReportId = workShiftReportId;
    }


    private WorkShiftReplenishmentReportModel() {
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
