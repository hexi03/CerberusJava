package com.hexi.Cerberus.domain.report.factorysite;

import com.hexi.Cerberus.domain.department.Department;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
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
public class FactorySiteReport extends Report {
    FactorySite factorySite;

    protected FactorySiteReport(){
        super();
    }
    public FactorySiteReport(
            ReportID id,
            FactorySite factorySite,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt) {
        super(id, factorySite.getParentDepartment(), createdAt, expirationDate, deletedAt);
        this.factorySite = factorySite;
    }

    public FactorySiteReport(
            FactorySite factorySite,
            Date createdAt,
            Date expirationDate) {
        super(factorySite.getParentDepartment(), createdAt, expirationDate);
        this.factorySite = factorySite;
    }


}
