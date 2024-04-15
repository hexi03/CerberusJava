package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.domain.item.Item;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class ReleaseReportModel extends WareHouseReportModel implements ItemStorageOperationReport {
    Report supplyReqReportId;
    Map<ItemModel, Integer> items = new HashMap<>();

    public ReleaseReportModel(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            Report supplyReqReportId,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.supplyReqReportId = supplyReqReportId;
        this.items = items;
    }

    public ReleaseReportModel(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Report supplyReqReportId,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate);
        this.supplyReqReportId = supplyReqReportId;
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
                                entry -> (Item) entry.getKey(),
                                entry -> entry.getValue()
                        )
                );
    }

}
