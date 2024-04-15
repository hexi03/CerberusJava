package com.hexi.Cerberus.adapter.persistence.report.base.factorysite;

import com.hexi.Cerberus.adapter.persistence.report.base.ReportModel;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.factorysite.FactorySiteReport;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;

@Getter
@Setter
public class FactorySiteReportModel extends ReportModel implements FactorySiteReport {
    FactorySite factorySite;

    protected FactorySiteReportModel() {
        super();
    }

    public FactorySiteReportModel(
            ReportID id,
            FactorySite factorySite,
            Date createdAt,
            Date expirationDate,
            Optional<Date> deletedAt) {
        super(id, factorySite.getParentDepartment(), createdAt, expirationDate, deletedAt);
        this.factorySite = factorySite;
    }

    public FactorySiteReportModel(
            ReportID id,
            FactorySite factorySite,
            Date createdAt,
            Date expirationDate
    ) {
        super(id, factorySite.getParentDepartment(), createdAt, expirationDate);
        this.factorySite = factorySite;
    }

    @Override
    public FactorySiteID getFactorySiteId() {
        return factorySite.getId();
    }

//    public FactorySiteReportModel(
//            FactorySite factorySite,
//            Date createdAt,
//            Date expirationDate) {
//        super(factorySite.getParentDepartment(), createdAt, expirationDate);
//        this.factorySite = factorySite;
//    }


}