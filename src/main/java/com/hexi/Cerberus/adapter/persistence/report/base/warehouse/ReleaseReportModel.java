package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.SupplyRequirementReport;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Access(AccessType.FIELD)
public class ReleaseReportModel extends WareHouseReportModel implements ItemStorageOperationReport {
    SupplyRequirementReport supplyReqReport;
    Map<ItemModel, Integer> items = new HashMap<>();

    public ReleaseReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            SupplyRequirementReport supplyReqReport,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.supplyReqReport = supplyReqReport;
        this.items = items;
    }

    public ReleaseReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            SupplyRequirementReport supplyReqReport,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate);
        this.supplyReqReport = supplyReqReport;
        this.items = items;
    }


    private ReleaseReportModel() {
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
