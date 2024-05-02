package com.hexi.Cerberus.domain.report.query.filter;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import com.hexi.Cerberus.domain.report.ReportID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReleaseReportFilterCriteria extends WareHouseReportFilterCriteria {
    FactorySiteID targetFactorySiteId;
    ReportID supplyReqReportId;
}
