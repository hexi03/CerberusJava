package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.item.base.ItemModel;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventarisationReportModel extends WareHouseReportModel {
    Map<ItemModel, Integer> items = new HashMap<>();

    public InventarisationReportModel(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate, deletedAt);
        this.items = items;
    }

    public InventarisationReportModel(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Map<ItemModel, Integer> items) {
        super(id, wareHouse, createdAt, expirationDate);
        this.items = items;
    }

    private InventarisationReportModel() {
        super();
    }

}
