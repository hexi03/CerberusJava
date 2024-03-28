package com.hexi.Cerberus.domain.report.warehouse;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;

@Getter
@Setter

public abstract class WareHouseReport extends Report {
    WareHouse wareHouse;

    protected WareHouseReport(){
        super();
    }
    public WareHouseReport(
            ReportID id,
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt) {
        super(id, wareHouse.getParentDepartment(), createdAt, expirationDate, deletedAt);
        this.wareHouse = wareHouse;
    }

    public WareHouseReport(
            WareHouse wareHouse,
            Date createdAt,
            Date expirationDate) {
        super(wareHouse.getParentDepartment(), createdAt, expirationDate);
        this.wareHouse = wareHouse;
    }
}
