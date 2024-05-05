package com.hexi.Cerberus.adapter.persistence.report.base.warehouse;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.adapter.persistence.warehouse.base.WareHouseModel;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.warehouse.WareHouseReport;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Optional;

@Entity
@Access(AccessType.FIELD)
public abstract class WareHouseReportModel extends ReportModel implements WareHouseReport {
    @ManyToOne(cascade = CascadeType.ALL)
    WareHouseModel wareHouse;

    protected WareHouseReportModel() {
        super();
    }

    public WareHouseReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt) {
        super(id, (DepartmentModel) wareHouse.getParentDepartment(), createdAt, expirationDate, deletedAt);
        this.wareHouse = wareHouse;
    }

    public WareHouseReportModel(
            ReportID id,
            WareHouseModel wareHouse,
            Date createdAt,
            Date expirationDate
    ) {
        super(id, (DepartmentModel) wareHouse.getParentDepartment(), createdAt, expirationDate);
        this.wareHouse = wareHouse;
    }

    @Override
    public WareHouse getWareHouse() {
        return (WareHouse) wareHouse;
    }

}
