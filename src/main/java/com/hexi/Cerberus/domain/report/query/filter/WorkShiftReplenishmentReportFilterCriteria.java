package com.hexi.Cerberus.domain.report.query.filter;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.report.ReportID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class WorkShiftReplenishmentReportFilterCriteria extends WareHouseReportFilterCriteria {
    FactorySiteID targetFactorySiteId;
    ReportID workShiftReportId;
}
