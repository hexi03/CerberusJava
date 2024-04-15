package com.hexi.Cerberus.domain.report.query.filter;

import com.hexi.Cerberus.domain.factorysite.FactorySiteID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class FactorySiteReportFilterCriteria extends ReportFilterCriteria {
    FactorySiteID factorySite;

}
