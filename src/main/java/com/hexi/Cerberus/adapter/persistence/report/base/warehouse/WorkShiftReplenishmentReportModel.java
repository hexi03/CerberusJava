package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.report.base.factorysite.WorkShiftReportModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Access(AccessType.FIELD)
public class WorkShiftReplenishmentReportModel extends WareHouseReportModel implements ItemStorageOperationReport {
    WorkShiftReportModel workShiftReport;
    Map<ItemModel, Integer> items = new HashMap<>();
    //Невостребованные остатки на возврат
    Map<ItemModel, Integer> unclaimedRemains;

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
        this.items = items;
        this.unclaimedRemains = unclaimedRemains;
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
        this.items = items;
        this.unclaimedRemains = unclaimedRemains;
        this.workShiftReport = workShiftReport;
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
                                entry -> entry.getKey(),
                                entry -> entry.getValue()
                        )
                );
    }

}
