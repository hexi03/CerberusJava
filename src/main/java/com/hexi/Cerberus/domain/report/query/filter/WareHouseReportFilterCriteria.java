package com.hexi.Cerberus.domain.report.query.filter;

import com.hexi.Cerberus.domain.warehouse.WareHouseID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class WareHouseReportFilterCriteria extends ReportFilterCriteria {
    WareHouseID warehouse;
}
