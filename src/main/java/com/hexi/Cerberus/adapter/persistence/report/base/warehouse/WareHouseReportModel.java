package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;

import java.util.Date;
import java.util.Optional;


public abstract class WareHouseReportModel extends ReportModel {
    WareHouse wareHouse;

    protected WareHouseReportModel() {
        super();
    }

    public WareHouseReportModel(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt) {
        super(id, wareHouse.getParentDepartment(), createdAt, expirationDate, deletedAt);
        this.wareHouse = wareHouse;
    }

    public WareHouseReportModel(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate
    ) {
        super(id, wareHouse.getParentDepartment(), createdAt, expirationDate);
        this.wareHouse = wareHouse;
    }

}
